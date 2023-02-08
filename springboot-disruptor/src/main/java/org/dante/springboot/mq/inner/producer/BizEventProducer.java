package org.dante.springboot.mq.inner.producer;

import org.dante.springboot.mq.inner.event.BizEvent;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 事件消息生产者
 * 
 * @author dante
 *
 */
public class BizEventProducer<T> {

	// 存储数据的环形队列
	private final RingBuffer<BizEvent<T>> ringBuffer;

	public BizEventProducer(RingBuffer<BizEvent<T>> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(BizEvent<T> bizEvent) {
		if (bizEvent != null) {
			ringBuffer.publishEvent((EventTranslatorOneArg<BizEvent<T>, T>) (event, sequence, payload) -> {
				event.setPayload(payload);
			}, bizEvent.getPayload());
		}

	}
}
