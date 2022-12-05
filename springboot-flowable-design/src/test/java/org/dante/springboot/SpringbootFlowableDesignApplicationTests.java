package org.dante.springboot;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.service.IFlowTaskService;
import org.dante.springboot.vo.ArgVO;
import org.dante.springboot.vo.FlowTaskVO;
import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootFlowableDesignApplicationTests {
	
	@Autowired
	protected IFlowInstanceService flowInstanceService;
	@Autowired
	protected IFlowTaskService flowTaskService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected ManagementService managementService;
	
	protected void startProcessInstance(String processDefKey) {
		this.startProcessInstance(processDefKey, null, false);
	}
	
	protected void startProcessInstance(String processDefKey, Map<String,Object> variableMap) {
		this.startProcessInstance(processDefKey, variableMap, false);
	}
	
	protected void startProcessInstance(String processDefKey, Map<String,Object> variableMap, boolean autoCompleteTask) {
		StartFlowInstanceVO vo = new StartFlowInstanceVO();
		vo.setProcessDefKey(processDefKey);
		vo.setBussinessKey(IdUtil.objectId());
		vo.setStarterId("dante");
		// 设置自动跳过
		vo.addParams(FlowEnum.FLOW_ARG_AUTO_SKIP.code(), Boolean.FALSE);
		if(!CollectionUtils.isEmpty(variableMap)) {
			vo.setParams(variableMap);
		}
		try {
			ProcessInstance processInstance = flowInstanceService.startProcessInstance(vo, autoCompleteTask);
			log.info("创建的流程实例：{}", processInstance.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void complete(String assign, String comment) {
		this.complete(assign, comment, null, null);
	}
	
	protected void complete(String assign, String comment, Map<String,Object> variableMap) {
		this.complete(assign, comment, variableMap, null);
	}
	
	protected void complete(String assign, String comment, ArgVO moreApproval) {
		this.complete(assign, comment, null, moreApproval);
	}
	
	protected void complete(String assign, String comment, Map<String,Object> variableMap, ArgVO moreApproval) {
		List<Task> tasks = flowTaskService.todoList(assign);
		if(CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", assign);
			return;
		}
		Task task = tasks.get(0);
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(assign);
		flowTaskVO.setCommentValue(comment);
		if(!CollectionUtils.isEmpty(variableMap)) {
			flowTaskVO.setParams(variableMap);
		}
		flowTaskVO.addParams("skip", Boolean.TRUE);
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
		
		if(moreApproval != null) {
			runtimeService.addMultiInstanceExecution(moreApproval.getActivityId(), task.getProcessInstanceId(), Collections.singletonMap(FlowEnum.FLOW_CANDIDATE.code(), (Object) moreApproval.getApproval()));
		}
	}
	
	
	
}
