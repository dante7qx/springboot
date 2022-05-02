package org.dante.springboot.service.impl;

import java.util.Map;

import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.service.FlowServiceFactory;
import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

@Service
public class FlowInstanceServiceImpl extends FlowServiceFactory implements IFlowInstanceService {

	@Override
	public ProcessInstance startProcessInstance(StartFlowInstanceVO vo) throws Exception {
		return this.startProcessInstance(vo, true);
	}
	
	@Override
	public ProcessInstance startProcessInstance(StartFlowInstanceVO vo, boolean autoCompleteTask) throws Exception {
		Assert.hasText(vo.getProcessDefKey(), "流程定义Key不能为空！");
		Assert.hasText(vo.getBussinessKey(), "业务标识不能为空！");
		Assert.hasText(vo.getStarterId(), "发起人不能为空！");
		ProcessInstance processInstance = null;

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(vo.getProcessDefKey()).latestVersion().singleResult();
		if (processDefinition == null) {
			throw new Exception("流程[" + vo.getProcessDefKey() + "]未定义。");
		} else if (processDefinition.isSuspended()) {
			throw new Exception("流程[" + vo.getProcessDefKey() + "]已挂起，不能发起流程。");
		}

		// 启动流程
		try {
			// 设置流程发起人
			identityService.setAuthenticatedUserId(vo.getStarterId());
			// 设置任务的处理人（流程的发起人 initiator）
			vo.addParams(FlowEnum.FLOW_INITIATOR.code(), vo.getStarterId());
			processInstance = runtimeService.createProcessInstanceBuilder()
					.processDefinitionId(processDefinition.getId()).name(processDefinition.getName())
					.businessKey(vo.getBussinessKey()).variables(vo.getParams()).start();
			// 起始任务
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			// 设置审批意见
			if (StringUtils.hasText(vo.getCommentValue())) {
				if (StringUtils.hasText(vo.getCommentType())) {
					taskService.addComment(task.getId(), processInstance.getId(), vo.getCommentType(),
							vo.getCommentValue());
				} else {
					taskService.addComment(task.getId(), processInstance.getId(), vo.getCommentValue());
				}
			}
			// 提交操作，完成当前任务
			if(autoCompleteTask) {
				taskService.complete(task.getId(), vo.getParams());
			}
		} catch (Exception e) {
			identityService.setAuthenticatedUserId(null);
		}

		return processInstance;
	}

	@Override
	public void deleteProcessInstance(String processInstanceId) throws Exception {
		runtimeService.deleteProcessInstance(processInstanceId, "流程实例" + processInstanceId + "删除");
		historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
				.deleteWithRelatedData();
	}

	private Map<String, Object> buildFlowVariable(Map<String, Object> bizParams) {
		Map<String, Object> variables = Maps.newHashMap();
		// 流程发起人
		if (bizParams.containsKey(FlowEnum.FLOW_INITIATOR.code())) {
			variables.put(FlowEnum.FLOW_INITIATOR.code(), bizParams.get(FlowEnum.FLOW_INITIATOR.code()).toString());
		}

		/** 多实例 **/
		// 设置下一个任务的候选组
		if (bizParams.containsKey(FlowEnum.FLOW_CANDIDATE_ROLE_LIST.code())) {
//			variables.put(FlowEnum.FLOW_CANDIDATE_ROLE_LIST.code(), "当前处理人所属的组"); // 实际业务逻辑
			variables.put(FlowEnum.FLOW_CANDIDATE_ROLE_LIST.code(),
					bizParams.get(FlowEnum.FLOW_CANDIDATE_ROLE_LIST.code()));
		}
		// 设置下一个任务的候选人
		if (bizParams.containsKey(FlowEnum.FLOW_CANDIDATE_USER_LIST.code())) {
//			variables.put(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(), "当前处理人所属的组的人"); // 实际业务逻辑
			variables.put(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(),
					bizParams.get(FlowEnum.FLOW_CANDIDATE_USER_LIST.code()));
		}
		/** 多实例 **/
		// 设置下一个任务的处理人（组）
		if (bizParams.containsKey(FlowEnum.FLOW_CANDIDATE.code())) {
			variables.put(FlowEnum.FLOW_CANDIDATE.code(), bizParams.get(FlowEnum.FLOW_CANDIDATE.code()));
		}
		// 当前任务的流转命令，决定下一步到那个任务
		if (bizParams.containsKey(FlowEnum.FLOW_COMMAND.code())) {
			variables.put(FlowEnum.FLOW_COMMAND.code(), bizParams.get(FlowEnum.FLOW_COMMAND.code()));
		}
		// 流程结束标识
		if (bizParams.containsKey(FlowEnum.FLOW_FINISHED.code())) {
			variables.put(FlowEnum.FLOW_FINISHED.code(), bizParams.get(FlowEnum.FLOW_FINISHED.code()));
		}
		return variables;
	}

}
