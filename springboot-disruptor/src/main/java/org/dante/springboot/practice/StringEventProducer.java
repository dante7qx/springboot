package org.dante.springboot.practice;

import com.lmax.disruptor.RingBuffer;

import lombok.extern.slf4j.Slf4j;

/**
 * 事件生产者
 * 
 * 每当业务要生产一个事件时，就会调用事件生产者的onData方法，将业务数据作为入参传进来，
 * 此时生产者会从环形队列中取出一个事件实例（就是前面的事件工厂创建的），把业务数据传给这个实例，再把实例正式发布出去
 * 
 * @author dante
 *
 */
@Slf4j
public class StringEventProducer {

	// 存储数据的环形队列
	private final RingBuffer<StringEvent> ringBuffer;

	public StringEventProducer(RingBuffer<StringEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(String content) {
		// ringBuffer是个队列，其next方法返回的是下最后一条记录之后的位置，这是个可用位置
		long sequence = ringBuffer.next();

		try {
			// sequence位置取出的事件是空事件
			StringEvent stringEvent = ringBuffer.get(sequence);
			// 空事件添加业务信息
			stringEvent.setValue(content);
		} finally {
			// 发布
			log.info("发布消息，序号 {}", sequence);
			ringBuffer.publish(sequence);
		}
	}
}
