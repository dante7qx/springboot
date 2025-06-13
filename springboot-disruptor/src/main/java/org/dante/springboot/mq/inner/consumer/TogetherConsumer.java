package org.dante.springboot.mq.inner.consumer;

import com.lmax.disruptor.EventHandler;
import org.dante.springboot.mq.inner.event.BizEvent;

/**
 * 共同事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface TogetherConsumer<T> extends EventHandler<BizEvent<T>> {

}
