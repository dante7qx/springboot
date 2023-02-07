package org.dante.springboot.mq;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.dante.springboot.SpringbootDisruptorApplicationTests;
import org.dante.springboot.mq.inner.InnerMQService;
import org.dante.springboot.mq.inner.event.BizEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class TestTogetherInnerMQServiceTest extends SpringbootDisruptorApplicationTests {
	
	@Autowired
	@Qualifier("testInnerMQService")
	private InnerMQService<Order> innerMQService;
	
	private static int EVENT_COUNT = 5;
	
	@Test
	public void testInnerMQ() throws Exception  {
		sendMsgAndRecevice();
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
	}
	
	@Test
	public void testInnerMQConcurrency() {
		IntStream.range(0, 2).parallel().forEach(n -> {
			try {
				sendMsgAndRecevice();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		});
		ThreadUtil.sleep(20, TimeUnit.SECONDS);
	}
	
	private void sendMsgAndRecevice() throws Exception {
		for (int i = 1; i <= EVENT_COUNT; i++) {
			Order order = new Order();
			order.setSeq(i);
			order.setOrderNo("订单编号[" + i + "]");
			order.setOrderDetail("空调订单");
			order.setPrice(7999D);
			
			BizEvent<Order> event = new BizEvent<>();
			event.setType("Email");
			event.setPayload(order);
			
			log.info("==================================================");
			log.info("发布订单消息 -> {}", event);
			innerMQService.publish(event);
		}
	}
	
}
