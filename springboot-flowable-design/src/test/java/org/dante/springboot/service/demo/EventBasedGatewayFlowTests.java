package org.dante.springboot.service.demo;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于事件的网关的出口顺序流与一般的顺序流不同。这些顺序流从不实际执行。相反，它们用于告知流程引擎：当执行到达一个基于事件的网关时，需要订阅什么事件。有以下限制：
 * 
 * (1) 一个基于事件的网关，必须有两条或更多的出口顺序流。
 * (2) 基于事件的网关，只能连接至intermediateCatchEvent（捕获中间事件）类型的元素（Flowable不支持在基于事件的网关之后连接“接收任务 Receive Task”）。
 * (3) 连接至基于事件的网关的intermediateCatchEvent，必须只有一个入口顺序流。
 * 
 * @author dante
 */
@Slf4j
public class EventBasedGatewayFlowTests extends SpringbootFlowableDesignApplicationTests {

	private static final String SIGNAL_KEY = "alert";
	
	@Test
	public void startProcessInstance() {
		this.startProcessInstance("EVENT_BASED_GATEWAY_FLOW");
	}
	
	/**
	 * 触发警告事件
	 * 
	 * （信号范围需要定义为全局）
	 */
	@Test
	public void triggerSignalEvent() {
		log.info("触发信号事件");
		runtimeService.signalEventReceived(SIGNAL_KEY);
		/*
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey("EVENT_BASED_GATEWAY_FLOW")
				.active().list();
		if(!CollectionUtils.isEmpty(processInstances)) {
			final String processInstanceId = processInstances.get(0).getId();
			List<Execution> executions = runtimeService.createExecutionQuery()
					.processInstanceId(processInstanceId)
					.signalEventSubscriptionName(SIGNAL_KEY)
					.list();
			log.info("{} -> {}", SIGNAL_KEY, executions.size());
			if (!CollectionUtils.isEmpty(executions)) {
				executions.forEach(e -> {
					log.info("{} - {}", processInstanceId, e.getId());
					runtimeService.signalEventReceived(SIGNAL_KEY, e.getId());
				});
			}
		}
		*/
	}
	
	@Test
	public void complete() {
		String currentUserId = "zhangsan";
		this.complete(currentUserId, "本王完成了");
	}
}
