package org.dante.springboot.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dante.springboot.cmd.AddHisCommentCmd;
import org.dante.springboot.consts.IConstant;
import org.dante.springboot.dao.AttachmentDAO;
import org.dante.springboot.dao.DeployProcessDAO;
import org.dante.springboot.dto.DeployProcessDTO;
import org.dante.springboot.enums.CommentTypeEnum;
import org.dante.springboot.po.AttachmentPO;
import org.dante.springboot.po.DeployProcessPO;
import org.dante.springboot.service.FlowProcessDiagramGenerator;
import org.dante.springboot.service.IFlowBpmnModelService;
import org.dante.springboot.service.IFlowService;
import org.dante.springboot.vo.CommentVo;
import org.dante.springboot.vo.StartProcessInstanceVO;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class FlowServiceImpl implements IFlowService {

	@Autowired
	private DeployProcessDAO deployProcessDAO;
	@Autowired
	private AttachmentDAO attachmentDAO;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private IFlowBpmnModelService bpmnModelService;
	@Autowired
	private FlowProcessDiagramGenerator flowProcessDiagramGenerator;
	@Autowired
	private IdentityService identityService;
	@Autowired
    private ManagementService managementService;

	@Override
	public List<DeployProcessPO> findDeployProcess() {
		return deployProcessDAO.findAll(Sort.by(Direction.DESC, "createTime"));
	}

	@Transactional
	@Override
	public DeployProcessPO saveDeployProcess(MultipartFile[] attachments, DeployProcessDTO deployProcessDTO)
			throws IOException {
		MultipartFile attachment = attachments[0];

		Deployment deploy = repositoryService.createDeployment()
				.addInputStream(attachment.getOriginalFilename(), attachment.getInputStream()).deploy();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.deploymentId(deploy.getId()).latestVersion().singleResult();

		Date currentDate = Date.from(Instant.now());

		FileCopyUtils.copy(attachment.getBytes(),
				new File(IConstant.FILE_PATH_ROOT.concat(attachment.getOriginalFilename())));

		AttachmentPO attachmentPO = new AttachmentPO();
		attachmentPO.setFileName(attachment.getOriginalFilename());
		attachmentPO.setCreateTime(currentDate);
		attachmentDAO.save(attachmentPO);

		DeployProcessPO deployProcessPO = new DeployProcessPO();
		deployProcessPO.setProcessName(deployProcessDTO.getProcessName());
		deployProcessPO.setDeploymentId(deploy.getId());
		deployProcessPO.setProcessDefinitionId(processDefinition.getId());
		deployProcessPO.setAttachment(attachmentPO);
		deployProcessPO.setCreateTime(currentDate);
		deployProcessDAO.save(deployProcessPO);

		return deployProcessPO;
	}

	@Override
	public DeployProcessPO findDeployProcessById(Long id) {
		return deployProcessDAO.getById(id);
	}

	@Override
	public byte[] findFlowChartByDeployProcessId(Long deployProcessId) throws IOException {
		DeployProcessPO deployProcessPO = deployProcessDAO.getById(deployProcessId);
		ProcessDefinition processDefinition = repositoryService
				.getProcessDefinition(deployProcessPO.getProcessDefinitionId());
		InputStream resourceAsStream = repositoryService.getResourceAsStream(deployProcessPO.getDeploymentId(),
				processDefinition.getDiagramResourceName());
		return FileCopyUtils.copyToByteArray(resourceAsStream);
	}

	@Override
	public byte[] findRuntimeFlowChart(String processInstanceId) throws Exception {
		// 1.获取当前的流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		
		String processDefinitionId = null;
		List<String> activeActivityIds = null;

		// 2.获取所有的历史轨迹对象
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).list();
		Map<String, HistoricActivityInstance> hisActivityMap = new HashMap<>();
		list.forEach(historicActivityInstance -> {
			if (!hisActivityMap.containsKey(historicActivityInstance.getActivityId())) {
				hisActivityMap.put(historicActivityInstance.getActivityId(), historicActivityInstance);
			}
		});

		if (processInstance != null) {
			processDefinitionId = processInstance.getProcessDefinitionId();
			activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
		} else {
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			processDefinitionId = historicProcessInstance.getProcessDefinitionId();
			activeActivityIds = new ArrayList<>();
			List<EndEvent> endEvents = bpmnModelService.findEndFlowElement(processDefinitionId);
			List<String> finalActiveActivityIds = activeActivityIds;
			endEvents.forEach(endEvent -> {
				if (hisActivityMap.containsKey(endEvent.getId())) {
					finalActiveActivityIds.add(endEvent.getId());
				}
			});
		}

		List<FlowNode> flowNodes = bpmnModelService.findFlowNodes(processDefinitionId);
		Map<String, FlowNode> activityMap = flowNodes.stream()
				.collect(Collectors.toMap(FlowNode::getId, flowNode -> flowNode));
		List<String> highLightedFlows = new ArrayList<>();
		activeActivityIds.forEach(activeActivityId -> this.getHighLightedFlows(activityMap, hisActivityMap,
				activeActivityId, highLightedFlows, activeActivityId));
		BpmnModel bpmnModel = bpmnModelService.getBpmnModelByProcessDefId(processDefinitionId);
		InputStream inputStream = flowProcessDiagramGenerator.generateDiagram(bpmnModel, activeActivityIds,
				highLightedFlows);
		byte[] datas = IoUtil.readInputStream(inputStream, "image inputStream name");

		return datas;
	}

	private void getHighLightedFlows(Map<String, FlowNode> flowNodeMap,
			Map<String, HistoricActivityInstance> hisActivityMap, String activeActivityId,
			List<String> highLightedFlows, String oldActivityId) {
		FlowNode flowNode = flowNodeMap.get(activeActivityId);
		List<SequenceFlow> incomingFlows = flowNode.getIncomingFlows();
		for (SequenceFlow sequenceFlow : incomingFlows) {
			String sourceRefId = sequenceFlow.getSourceRef();
			if (hisActivityMap.containsKey(sourceRefId) && !oldActivityId.equals(sourceRefId)) {
				highLightedFlows.add(sequenceFlow.getId());
//				this.getHighLightedFlows(flowNodeMap, hisActivityMap, sourceRefId, highLightedFlows, oldActivityId);
			} else {
				if (hisActivityMap.containsKey(sourceRefId)) {
					highLightedFlows.add(sequenceFlow.getId());
				}
				break;
			}
		}
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(StartProcessInstanceVO params) throws Exception {
		ProcessInstance processInstance = null;
		ProcessDefinition processDefinition = null;
		String commentStr = ""; // 登记、提交
		String commentType = ""; // 登记、提交
		if (StringUtils.hasText(params.getProcessDefinitionKey()) && StringUtils.hasText(params.getBusinessKey())
				&& StringUtils.hasText(params.getSystemSn())) {
			processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionKey(params.getProcessDefinitionKey()).latestVersion().singleResult();
			if (processDefinition != null && processDefinition.isSuspended()) {
				throw new Exception("此流程已经挂起，请联系系统管理员！");
			}
		}
		/**
		 * 1、设置变量 
		 * 1.1、设置提交人字段为空字符串让其自动跳过 
		 * 1.2、设置可以自动跳过 
		 * 1.3、汇报线的参数设置
		 */
		if (IConstant.OPER_INSERT.equalsIgnoreCase(params.getOperType())) {
			// 保存操作，不自动跳过任务
			params.getVariables().put(IConstant.FLOW_SUBMITTER_VAR, params.getSubmitter());
			params.getVariables().put(IConstant.FLOWABLE_SKIP_EXPRESSION_ENABLED, false);
			commentStr = "登记";
		} else {
			// 设置提交人字段为空字符串让其自动跳过
			params.getVariables().put(IConstant.FLOW_SUBMITTER_VAR, "");
			params.getVariables().put(IConstant.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);
			commentStr = "提交";
		}
		
		// 3.启动流程
		identityService.setAuthenticatedUserId(params.getApplicant());
		params.getVariables().put(IConstant.FLOW_ASSIGNEE_VAR, params.getApplicant()); 
		
		processInstance = runtimeService.createProcessInstanceBuilder()
				.processDefinitionId(processDefinition.getId())
				.name(params.getFormName())
				.businessKey(params.getBusinessKey())
				.variables(params.getVariables())
				.tenantId(params.getSystemSn())
		.start();
		
		//4.添加审批记录
		CommentVo comment = this.buildCommnetVO(params.getSubmitter(), processInstance.getProcessInstanceId(),
                CommentTypeEnum.TJ.toString(), params.getFormName() + commentStr);
		managementService.executeCommand(new AddHisCommentCmd(comment.getTaskId(), comment.getUserId(), comment.getProcessInstanceId(),
                comment.getType(), comment.getMessage()));
        //5.TODO 推送消息数据
		return processInstance;
	}

	@Override
	public void addComment(CommentVo comment) {
		managementService.executeCommand(new AddHisCommentCmd(comment.getTaskId(), comment.getUserId(), comment.getProcessInstanceId(),
                comment.getType(), comment.getMessage()));
		
	}

	@Override
	public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CommentVo buildCommnetVO(String taskId, String userCode, String processInstanceId, String type, String message) {
		//添加备注
        return new CommentVo(taskId, userCode, processInstanceId, type, message);
	}
	
	private CommentVo buildCommnetVO(String userCode, String processInstanceId, String type, String message) {
		return this.buildCommnetVO(null , userCode, processInstanceId, type, message);
	}

}
