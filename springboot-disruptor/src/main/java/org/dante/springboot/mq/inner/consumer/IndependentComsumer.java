package org.dante.springboot.mq.inner.consumer;

import org.dante.springboot.mq.inner.event.BizEvent;

import com.lmax.disruptor.EventHandler;

/**
 * 独立事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface IndependentComsumer <T> extends EventHandler<BizEvent<T>> {

}
