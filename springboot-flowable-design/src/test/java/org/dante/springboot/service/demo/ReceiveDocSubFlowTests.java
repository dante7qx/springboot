package org.dante.springboot.service.demo;

import java.util.List;
import java.util.UUID;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.enums.LeaveEnum;
import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.service.IFlowTaskService;
import org.dante.springboot.vo.FlowTaskVO;
import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveDocSubFlowTests extends SpringbootFlowableDesignApplicationTests {
	
	@Autowired
	private IFlowInstanceService flowInstanceService;
	@Autowired
	private IFlowTaskService flowTaskService;
	
	private final String startUserId = "zhangsan";
	
	@Test
	public void startProcessInstance() {
		StartFlowInstanceVO vo = new StartFlowInstanceVO();
//		vo.setProcessDefKey(FlowEnum.FLOW_DEF_KEY_RECE_VE_DOC_SUB_FLOW.code());
//		vo.setProcessDefKey("NESTED_SUB_FLOW");
//		vo.setProcessDefKey("NESTED_SUB_FLOW_GATEWAY");
//		vo.setProcessDefKey("NESTED_SUB_FLOW_GATEWAY2");
		vo.setProcessDefKey("PARALLEL_GATEWAY_FLOW");
		
		vo.setBussinessKey(UUID.randomUUID().toString().toLowerCase());
		vo.setCommentType(LeaveEnum.TJ.code());
		vo.setCommentValue(LeaveEnum.TJ.value());
		vo.setStarterId(startUserId);
//		vo.addParams(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
		try {
			ProcessInstance processInstance = flowInstanceService.startProcessInstance(vo);
			log.info("创建的流程实例：{}", processInstance.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void complete() {
		String currentUserId = "dante";
//		String currentUserId = "zhangsan";
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		if(CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", currentUserId);
			return;
		}
		Task task = tasks.get(0);
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(currentUserId);
		flowTaskVO.addParams(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
//		flowTaskVO.addParams(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
//		flowTaskVO.addParams("turnIssue", Boolean.TRUE);
		// reIssued
		flowTaskVO.setCommentValue("同意");
		
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
	
	@Test
	public void complete2() {
		String currentUserId = "dante";
//		String currentUserId = "zhangsan";
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		if(CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", currentUserId);
			return;
		}
		Task task = tasks.get(0);
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(currentUserId);
//		flowTaskVO.addParams(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.TRUE);
//		flowTaskVO.addParams(FlowEnum.FLOW_ARG_AGREE.code(), Boolean.FALSE);
//		flowTaskVO.addParams("reIssued", Boolean.TRUE);
//		flowTaskVO.addParams("reIssued", Boolean.FALSE);
		// reIssued
		flowTaskVO.setCommentValue("同意");
		
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
	
	/**
	 * 并行网关
	 */
	@Test
	public void complete3() {
//		String[] userIds = {"zhangsan", "wangwu"};
		String[] userIds = {"lisi", "dante"};
		for (String userId : userIds) {
			List<Task> tasks = flowTaskService.todoList(userId);
			if(CollectionUtils.isEmpty(tasks)) {
				log.info("{} 没有待办任务", userId);
				return;
			}
			Task task = tasks.get(0);
			FlowTaskVO flowTaskVO = new FlowTaskVO();
			flowTaskVO.setTaskId(task.getId());
			flowTaskVO.setCurrentUserId(userId);
			flowTaskVO.setCommentValue("同意");
			
			flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
		}
		
	}

}
