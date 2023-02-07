package org.dante.springboot.mq;

import org.dante.springboot.mq.inner.consumer.TogetherConsumer;
import org.dante.springboot.mq.inner.event.BizEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestTogetherConsumer implements TogetherConsumer<Order> {

	public TestTogetherConsumer() {
	}
	
	@Override
	public void onEvent(final BizEvent<Order> event) throws Exception {
		log.info("订单共同消费 -> {}", event);
		log.info("==================================================");
		if(event.getPayload().getSeq() == 3) {
			throw new Exception("业务消费错误！");
		}
	}

}
