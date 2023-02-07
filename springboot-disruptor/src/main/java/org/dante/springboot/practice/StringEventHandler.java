package org.dante.springboot.practice;

import java.util.function.Consumer;

import com.lmax.disruptor.EventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 事件消费者
 * 
 * 事件处理类的作用是定义一个事件如何被消费，里面是具体的业务代码，每个事件都会执行此类的onEvent方法
 * 
 * @author dante
 *
 */
@Slf4j
public class StringEventHandler implements EventHandler<StringEvent> {

	// 外部可以传入Consumer实现类，每处理一条消息的时候，consumer的accept方法就会被执行一次
	private Consumer<?> consumer;

	public StringEventHandler(Consumer<?> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
		log.info("接受消息：序号 [{}], 是否最后一个事件 [{}], 事件消息 : {}", sequence, endOfBatch, event);
		
		if(sequence == 3) {
			throw new Exception("StringEventHandler 4 抛出异常.");
		}
		
		// 这里延时100ms，模拟消费事件的逻辑的耗时
		Thread.sleep(100);

		// 如果外部传入了consumer，就要执行一次accept方法
		if (null != consumer) {
			consumer.accept(null);
		}
	}

}
