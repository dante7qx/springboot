package org.dante.springboot.practice;

import com.lmax.disruptor.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理类
 * 
 * @author dante
 *
 */
@Slf4j
public class StringEventExceptionHandler implements ExceptionHandler<StringEvent> {
	
	/** 补偿业务服务类 - 持久化数据到数据库 */
	private final IBizService bizService;
	
	public StringEventExceptionHandler(IBizService bizService) {
		super();
		this.bizService = bizService;
	}

	@Override
	public void handleEventException(Throwable ex, long sequence, StringEvent event) {
		log.error("handleEventException -> {}", ex.getMessage());
		bizService.handleBizEvent(event);
		log.error("{} -> {}", sequence, event);
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
