package org.dante.springboot.service.impl;

import java.util.List;

import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.service.FlowServiceFactory;
import org.dante.springboot.service.IFlowTaskService;
import org.dante.springboot.vo.FlowTaskVO;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FlowTaskServiceImpl extends FlowServiceFactory implements IFlowTaskService {

	@Override
	public List<Task> todoList(String userId) {
		// 已经签收过（有办理人 Assignee）的任务列表
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		if(CollectionUtils.isEmpty(tasks)) {
			// 候选组待签收的任务
			tasks = taskService.createTaskQuery().taskCandidateGroup(userId).list();
		} 
		if(CollectionUtils.isEmpty(tasks)) {
			// 候选人待签收的任务
			tasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		}
		return tasks;
	}

	@Override
	public void complete(String taskId, FlowTaskVO flowTaskVO) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.addComment(taskId, task.getProcessInstanceId(), flowTaskVO.getCommentType(), flowTaskVO.getCommentValue());
		DelegationState delegationState = task.getDelegationState();
		// 判断是否为委派的任务
		if(DelegationState.PENDING.compareTo(delegationState) == 0) {
			taskService.resolveTask(taskId, flowTaskVO.getParams());
		} else {
			taskService.setAssignee(taskId, flowTaskVO.getCurrentUserId());
			taskService.complete(taskId, flowTaskVO.getParams());
		}
		
	}
	
	@Override
	public void assignOtherUser(String taskId, String currentUserId, String acceptUserId, boolean keepMyTodo,
			String reason) {
		if(keepMyTodo) {
			taskService.setOwner(taskId, currentUserId);
			taskService.delegateTask(taskId, acceptUserId);
			taskService.setVariable(taskId, FlowEnum.FLOW_DELEGATE.code(), reason);
		} else {
			taskService.setOwner(taskId, currentUserId);
			taskService.setAssignee(taskId, acceptUserId);
			taskService.setVariable(taskId, FlowEnum.FLOW_TURN_TODO.code(), reason);
			
		}
	}

	@Override
	public void delegate(String taskId, String currentUserId, String acceptUserId, String reason) {
		taskService.setOwner(taskId, currentUserId);
		taskService.delegateTask(taskId, acceptUserId);
		taskService.setVariable(taskId, FlowEnum.FLOW_DELEGATE.code(), reason);
	}

	@Override
	public void turnTodo(String taskId, String currentUserId, String acceptUserId, String reason) {
		taskService.setOwner(taskId, currentUserId);
		taskService.setAssignee(taskId, acceptUserId);
		taskService.setVariable(taskId, FlowEnum.FLOW_TURN_TODO.code(), reason);
	}

	

}
