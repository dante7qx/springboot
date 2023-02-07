package org.dante.springboot.mq.inner.consumer;

import org.dante.springboot.mq.inner.event.BizEvent;

import com.lmax.disruptor.WorkHandler;

/**
 * 共同事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface TogetherConsumer<T> extends WorkHandler<BizEvent<T>> {

}
