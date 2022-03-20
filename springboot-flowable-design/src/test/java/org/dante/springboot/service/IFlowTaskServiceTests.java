package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.enums.LeaveEnum;
import org.dante.springboot.vo.FlowTaskVO;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class IFlowTaskServiceTests extends SpringbootFlowableDesignApplicationTests {
	
	private final String currentUserId = "lizhuren";
	private final String accpetUserId = "haizhuren";
	
	@Autowired
	private IFlowTaskService flowTaskService;
	
	@Test
	public void todoList() {
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		log.info("Tasks => {}", tasks);
	}
	
	@Test
	public void assignOtherUser() {
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		Task task = tasks.get(0);
		if(task == null) {
			log.info("{} 没有待办任务", currentUserId);
		}
		// 委派
		flowTaskService.assignOtherUser(task.getId(), currentUserId, accpetUserId, true, "我有事，请帮我处理！");
	}
	
	@Test
	public void completeWithDelegate() {
		List<Task> tasks = flowTaskService.todoList(accpetUserId);
		Task task = tasks.get(0);
		if(task == null) {
			log.info("{} 没有待办任务", accpetUserId);
		}
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(accpetUserId);
		// 拒绝
		flowTaskVO.addParams(FlowEnum.FLOW_COMMAND.code(), false);
		flowTaskVO.setCommentType(LeaveEnum.TH.code());
		flowTaskVO.setCommentValue("请填写请假原因");
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
	
	@Test
	public void complete() {
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		Task task = tasks.get(0);
		if(task == null) {
			log.info("{} 没有待办任务", accpetUserId);
		}
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(currentUserId);
		// 拒绝
		flowTaskVO.addParams(FlowEnum.FLOW_COMMAND.code(), true);
		flowTaskVO.setCommentType(LeaveEnum.TG.code());
		flowTaskVO.setCommentValue(LeaveEnum.TG.value());
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
	
	
}
