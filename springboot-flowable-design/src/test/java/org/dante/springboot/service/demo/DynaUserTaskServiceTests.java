package org.dante.springboot.service.demo;

import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.cmd.SpiritInjectParallelUserTaskCmd;
import org.dante.springboot.enums.FlowEnum;
import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynaUserTaskServiceTests extends SpringbootFlowableDesignApplicationTests {

	private static final String PRO_DEF_KEY = "DYNA_USER_TASK_1";

	@Autowired
	private DynamicBpmnService dynamicBpmnService;

	@Test
	public void startProcessInstance() {
		this.startProcessInstance(PRO_DEF_KEY);
	}

	@Test
	public void createDynaUserTaskSubProcess() {
		String curUserId = "dante";
		log.info("动态创建用户任务");
		List<Task> tasks = flowTaskService.todoList(curUserId);
		if (CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", curUserId);
			return;
		}
		Task task = tasks.get(0);
		DynamicUserTaskBuilder tb = new DynamicUserTaskBuilder();
		tb.id("dyna_task_tb2").name("动态用户任务（子任务）").assignee("lisi");
		dynamicBpmnService.injectParallelUserTask(task.getId(), tb);
	}

	@Test
	public void createDynaUserTaskProcessInstance() {
		String curUserId = "dante";
		log.info("动态创建用户任务");
		List<Task> tasks = flowTaskService.todoList(curUserId);
		if (CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", curUserId);
			return;
		}
		Task task = tasks.get(0);
		DynamicUserTaskBuilder tb = new DynamicUserTaskBuilder();
		tb.id("dyna_task_tb1").name("动态用户任务(流程实例)").assignee("zhangsan");
		dynamicBpmnService.injectUserTaskInProcessInstance(task.getProcessInstanceId(), tb);
	}

	@Test
	public void createDynaUserTaskBySpiritCmd() {
		String curUserId = "dante";
		log.info("动态创建用户任务");
		List<Task> tasks = flowTaskService.todoList(curUserId);
		if (CollectionUtils.isEmpty(tasks)) {
			log.info("{} 没有待办任务", curUserId);
			return;
		}
		Task task = tasks.get(0);
		
		DynamicUserTaskBuilder tb = new DynamicUserTaskBuilder();
		tb.id("dyna_task_tb").name("动态用户任务").assignee("zhangsan,lisi,wangwu");
//		tb.id("dyna_task_tb").name("动态用户任务").assignee("zhangsan");
		managementService.executeCommand(new SpiritInjectParallelUserTaskCmd(task.getId(), task.getProcessInstanceId(), tb));
		
		Map<String, Object> variableMap = Maps.newHashMap();
		variableMap.put(FlowEnum.FLOW_ARG_SUB_TASK.code(), Boolean.TRUE);
		this.complete(curUserId, "同意", variableMap);
	}

	@Test
	public void complete1() {
		String currentUserId = "zhangsan";
		String comment = "本皇完成了";
		this.complete(currentUserId, comment);
	}
	
	@Test
	public void complete2() {
		String currentUserId = "lisi";
		String comment = "本四完成了";
		this.complete(currentUserId, comment);
	}
	
	@Test
	public void complete3() {
		String currentUserId = "wangwu";
		String comment = "小五完成了";
		this.complete(currentUserId, comment);
	}

}
