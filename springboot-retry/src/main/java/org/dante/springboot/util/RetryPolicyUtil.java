package org.dante.springboot.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;

/**
 * 重试策略
 */
public class RetryPolicyUtil {

	/**
	 * 只允许调用RetryCallback一次，不允许重试
	 * 
	 * @return
	 */
	public static RetryPolicy getNeverRetryPolicy() {
		NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();
		return neverRetryPolicy;
	}

	/**
	 * 允许无限重试，直到成功，此方式逻辑不当会导致死循环
	 * 
	 * @return
	 */
	public static RetryPolicy getAlwaysRetryPolicy() {
		AlwaysRetryPolicy alwaysRetryPolicy = new AlwaysRetryPolicy();
		return alwaysRetryPolicy;
	}

	/**
	 * 固定次数重试策略，默认重试最大次数为3次，RetryTemplate默认使用的策略
	 * 
	 * @return
	 */
	public static RetryPolicy getSimpleRetryPolicy() {
		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(5);
		return simpleRetryPolicy;
	}

	/**
	 * 超时时间重试策略，默认超时时间为1秒，在指定的超时时间内允许重试
	 * 
	 * @return
	 */
	public static RetryPolicy getTimeoutRetryPolicy() {
		TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
		timeoutRetryPolicy.setTimeout(3000L);
		return timeoutRetryPolicy;
	}

	/**
	 * 设置不同异常的重试策略，类似组合重试策略，区别在于这里只区分不同异常的重试
	 * 
	 * @return
	 */
	public static RetryPolicy getExceptionClassifierRetryPolicy() {
		ExceptionClassifierRetryPolicy exceptionClassifierRetryPolicy = new ExceptionClassifierRetryPolicy();
		Map<Class<? extends Throwable>, RetryPolicy> map = new HashMap<>();
		map.put(RuntimeException.class, getNeverRetryPolicy());
		map.put(Exception.class, getSimpleRetryPolicy());
		exceptionClassifierRetryPolicy.setPolicyMap(map);
		return exceptionClassifierRetryPolicy;
	}

	/**
	 * 有熔断功能的重试策略，需设置3个参数openTimeout、resetTimeout和delegate
	 * 
	 * @return
	 */
	public static RetryPolicy getCircuitBreakerRetryPolicy() {
		CircuitBreakerRetryPolicy circuitBreakerRetryPolicy = new CircuitBreakerRetryPolicy(new SimpleRetryPolicy());
		circuitBreakerRetryPolicy.setOpenTimeout(5000);
		circuitBreakerRetryPolicy.setResetTimeout(20000);
		return circuitBreakerRetryPolicy;
	}

	/**
	 * 组合重试策略，有两种组合方式，乐观组合重试策略是指只要有一个策略允许重试即可以，悲观组合重试策略是指只要有一个策略不允许重试即可以，但不管哪种组合方式，组合中的每一个策略都会执行
	 * 
	 * @return
	 */
	public static RetryPolicy getCompositeRetryPolicy() {
		CompositeRetryPolicy compositeRetryPolicy = new CompositeRetryPolicy();
		// 设置是否乐观重试策略
		compositeRetryPolicy.setOptimistic(true);
		compositeRetryPolicy.setPolicies(new RetryPolicy[] { getSimpleRetryPolicy(), getTimeoutRetryPolicy() });
		return compositeRetryPolicy;

	}

}
