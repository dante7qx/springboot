package org.dante.springboot.practice;

import java.util.function.Consumer;

import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 消费订单事件的邮件服务 - 独立消费
 * 
 * @author dante
 *
 */
@Slf4j
public class MailEventHandler implements EventHandler<StringEvent> {

	// 外部可以传入Consumer实现类，每处理一条消息的时候，consumer的accept方法就会被执行一次
	private Consumer<?> consumer;
	
	public MailEventHandler(Consumer<?> consumer) {
        this.consumer = consumer;
    }

	@Override
	public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
		log.info("邮件服务 sequence [{}], endOfBatch [{}], event : {}", sequence, endOfBatch, event);

		// 这里延时100ms，模拟消费事件的逻辑的耗时
		Thread.sleep(100);

		// 如果外部传入了consumer，就要执行一次accept方法
		if (null != consumer) {
			consumer.accept(null);
		}

	}

}
