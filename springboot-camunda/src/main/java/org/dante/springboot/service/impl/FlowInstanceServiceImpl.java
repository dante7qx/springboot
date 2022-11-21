package org.dante.springboot.service.impl;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.dante.springboot.service.FlowServiceFactory;
import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.vo.StartFlowVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlowInstanceServiceImpl extends FlowServiceFactory implements IFlowInstanceService {

	@Override
	public ProcessInstance startFlowByProcKey(String procKey, StartFlowVO vo) throws Exception {
		Assert.hasText(procKey, "流程定义Key不能为空！");
		Assert.hasText(vo.getBizId(), "业务标识不能为空！");
		Assert.hasText(vo.getStartUserId(), "发起人不能为空！");
		
		ProcessInstance processInstance = null;

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(procKey).latestVersion().singleResult();
		if (processDefinition == null) {
			throw new Exception("流程[" + procKey + "]未定义。");
		} else if (processDefinition.isSuspended()) {
			throw new Exception("流程[" + procKey + "]已挂起，不能发起流程。");
		}
		
		// 启动流程
		try {
			// 设置流程发起人
			identityService.setAuthenticatedUserId(vo.getStartUserId());
			// 设置任务的处理人（流程的发起人 initiator）
			processInstance = runtimeService.createProcessInstanceById(processDefinition.getId())
					.businessKey(vo.getBizId())
					.setVariables(vo.getParams())
					.execute();
			// 起始任务
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			// 设置审批意见
			taskService.createComment(task.getId(), processInstance.getId(), StrUtil.isNotEmpty(vo.getComment()) ? vo.getComment() : task.getName());
			// 提交操作，完成当前任务
			taskService.complete(task.getId(), vo.getParams());
		} catch (Exception e) {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}
	
	/**
	 * 根据流程实例Id删除流程
	 * 
	 * @param procInsId
	 */
	@Transactional
	public void deleteFlowByProcInsId(String procInsId) {
		runtimeService.deleteProcessInstance(procInsId, "管理员删除");
		historyService.deleteHistoricProcessInstanceIfExists(procInsId);
	}

}
