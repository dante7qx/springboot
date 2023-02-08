package org.dante.springboot.mq.inner;

import org.dante.springboot.mq.inner.event.BizEvent;

import com.lmax.disruptor.EventHandler;

/**
 * 数据清理处理类
 * 
 * @author dante
 *
 * @param <T>
 */
public class CleaingEventHandler<T> implements EventHandler<BizEvent<T>> {

	@Override
	public void onEvent(BizEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("========================数据清理开始=======================");
		System.out.println(sequence + " <---> " + event);
		event.clear();
		System.out.println(sequence + " <---> " + event);
		System.out.println("========================数据清理结束=======================");
	}
	
}
