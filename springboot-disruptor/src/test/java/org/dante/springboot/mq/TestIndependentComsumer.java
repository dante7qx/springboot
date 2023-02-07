package org.dante.springboot.mq;

import org.dante.springboot.mq.inner.consumer.IndependentComsumer;
import org.dante.springboot.mq.inner.event.BizEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestIndependentComsumer implements IndependentComsumer<Order> {

	public TestIndependentComsumer() {}

	@Override
	public void onEvent(BizEvent<Order> event, long sequence, boolean endOfBatch) throws Exception {
		log.info("订单独立消费 -> {}", event);
		log.info("==================================================");
		if (event.getPayload()
			.getSeq() == 3) {
			throw new Exception("业务消费错误！");
		}
	}

}
