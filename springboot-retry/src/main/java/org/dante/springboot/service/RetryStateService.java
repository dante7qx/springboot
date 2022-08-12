package org.dante.springboot.service;

import java.time.LocalTime;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryState;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetryStateService {

	@CircuitBreaker(openTimeout = 3000L, resetTimeout = 7000L, maxAttempts = 5) // 最大重试次数
	public String retryAnnotationState() {
		log.warn("业务执行失败！！！" + LocalTime.now());
		throw new RuntimeException("timeout");
	}

	@Recover
	public String recover() {
		log.info("请求被拒绝。");
		return "default";
	}

	public void retryState(long sleepTime) {
		RetryTemplate template = RetryTemplate.builder().retryOn(Exception.class).build();
		CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(5));
		// 此参数用来判断熔断器是否打开的超时时间动
		retryPolicy.setOpenTimeout(2000L);
		// 此参数用来判断熔断器是否重置的超时时间
		retryPolicy.setResetTimeout(20000L);
		template.setRetryPolicy(retryPolicy);
		Object key = "retryState";
		boolean isForceRefresh = false;
		RetryState state = new DefaultRetryState(key, isForceRefresh);
		for (int i = 0; i < 20; i++) {
			try {
				String result = template.execute(new RetryCallback<String, RuntimeException>() {
					@Override
					public String doWithRetry(RetryContext context) throws RuntimeException {
						log.info("重试次数:" + context.getRetryCount());
						log.warn("业务执行失败！！！" + LocalTime.now());
						try {
							Thread.sleep(200L * 1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						throw new RuntimeException("timeout");
					}
				}, new RecoveryCallback<String>() {
					@Override
					public String recover(RetryContext context) throws Exception {
						log.warn("业务执行recover！！！" + LocalTime.now());
						return "default";
					}
				}, state);
				if (result.equals("default")) {
					try {
						log.info("请求被拒绝，休眠{}毫秒后再次请求。", sleepTime);
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
