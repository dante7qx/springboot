package org.dante.springboot.exception;

import com.gobrs.async.callback.AsyncTaskExceptionInterceptor;
import com.gobrs.async.callback.ErrorCallback;
import com.gobrs.async.util.JsonUtil;

import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 主流程中断异常自定义处理
 * 
 */
@Slf4j
@Component
public class GobrsExceptionInter implements AsyncTaskExceptionInterceptor {

	@Override
	@SneakyThrows
	public void exception(ErrorCallback errorCallback) {
		log.error("Execute global interceptor  error{}", JsonUtil.obj2String(errorCallback.getThrowable()));
	}
	
}
