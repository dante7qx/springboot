package org.dante.springboot.mq.inner.exception;

import org.dante.springboot.mq.inner.event.BizEvent;

import com.lmax.disruptor.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 内部事件消息全局异常处理类
 * 
 * @author dante
 *
 */
@Slf4j
public class InnerMQException<T> implements ExceptionHandler<BizEvent<T>> {
	
	@Override
	public void handleEventException(Throwable ex, long sequence, BizEvent<T> event) {
		// 补偿业务服务类 - 持久化数据到数据库
		log.error("{} - {}", sequence, event, ex);
		
	}

	@Override
	public void handleOnStartException(Throwable ex) {
		log.error("HandleOnStartException -> {}", ex.getMessage());
	}

	@Override
	public void handleOnShutdownException(Throwable ex) {
		log.error("HandleOnShutdownException -> {}", ex.getMessage());
		
	}

}
