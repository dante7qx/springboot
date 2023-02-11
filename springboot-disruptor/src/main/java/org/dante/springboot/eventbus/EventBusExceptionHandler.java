package org.dante.springboot.eventbus;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * 
 * @author dante
 *
 */
@Slf4j
public class EventBusExceptionHandler implements SubscriberExceptionHandler {

	@Override
	public void handleException(Throwable e, SubscriberExceptionContext context) {
		log.error(e.getMessage(), context.getEvent(), e);
	}

}
