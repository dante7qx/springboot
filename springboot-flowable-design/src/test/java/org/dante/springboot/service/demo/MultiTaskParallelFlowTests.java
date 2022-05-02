package org.dante.springboot.service.demo;

import java.util.Arrays;
import java.util.List;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.enums.FlowEnum;
import org.dante.springboot.service.IFlowInstanceService;
import org.dante.springboot.service.IFlowTaskService;
import org.dante.springboot.vo.FlowTaskVO;
import org.dante.springboot.vo.StartFlowInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiTaskParallelFlowTests extends SpringbootFlowableDesignApplicationTests {

	@Autowired
	private IFlowInstanceService flowInstanceService;
	@Autowired
	private IFlowTaskService flowTaskService;
	
	@Test
	public void startProcessInstance() {
		StartFlowInstanceVO vo = new StartFlowInstanceVO();
		vo.setProcessDefKey("MULTI_TASK_PARALLEL_FLOW");
		vo.setBussinessKey(IdUtil.objectId());
		vo.setStarterId("dante");
		// 设置自动跳过
		vo.addParams(FlowEnum.FLOW_ARG_AUTO_SKIP.code(), Boolean.TRUE);
		vo.addParams(FlowEnum.FLOW_CANDIDATE_USER_LIST.code(), Arrays.asList(new String[]{"lisi", "zhangsan"}));
		try {
			ProcessInstance processInstance = flowInstanceService.startProcessInstance(vo, false);
			log.info("创建的流程实例：{}", processInstance.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void complete1() {
		String currentUserId = "zhangsan";
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		if(CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", currentUserId);
			return;
		}
		Task task = tasks.get(0);
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(currentUserId);
		flowTaskVO.setCommentValue("本王完成了");
		flowTaskVO.addParams("skip", Boolean.TRUE);
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
	
	@Test
	public void complete2() {
		String currentUserId = "lisi";
		List<Task> tasks = flowTaskService.todoList(currentUserId);
		if(CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", currentUserId);
			return;
		}
		Task task = tasks.get(0);
		FlowTaskVO flowTaskVO = new FlowTaskVO();
		flowTaskVO.setTaskId(task.getId());
		flowTaskVO.setCurrentUserId(currentUserId);
		flowTaskVO.setCommentValue("本皇完成了");
		flowTaskVO.addParams("skip", Boolean.TRUE);
		flowTaskService.complete(flowTaskVO.getTaskId(), flowTaskVO);
	}
}
