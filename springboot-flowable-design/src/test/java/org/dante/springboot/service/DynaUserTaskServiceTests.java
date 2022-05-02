package org.dante.springboot.service;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.ParallelGateway;
import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.impl.dynamic.DynamicUserTaskBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynaUserTaskServiceTests extends SpringbootFlowableDesignApplicationTests {
	
	private static final String PRO_DEF_KEY = "DYNA_USER_TASK_1";
	
	@Autowired
	private DynamicBpmnService dynamicBpmnService;
	
	public void createDynaUserTask() {
		log.info("动态创建用户任务");
		DynamicUserTaskBuilder taskBuilder = new DynamicUserTaskBuilder();
		taskBuilder.id("DynaTask_1").name("动态用户任务1").assignee("dante");
		dynamicBpmnService.injectParallelUserTask("已定义任务Id", taskBuilder);
	}
	
	/**
	 * 创建结束事件
	 * 
	 * @return
	 */
	private EndEvent createEndEvent() {
		EndEvent completeEvent = new EndEvent();
        completeEvent.setId("complete");
        completeEvent.setName("结束");
        return completeEvent;
	}
	
	/**
	 * 创建并行网关
	 * 
	 * @return
	 */
	private ParallelGateway createParallelGateway() {
		ParallelGateway parallelGateway = new ParallelGateway();
		parallelGateway.setId("parallelGateway_".concat(IdUtil.objectId()));
		return parallelGateway;
	}
	
	public static void main(String[] args) {
	}

}
