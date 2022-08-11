package org.dante.springboot.util;

import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;

/**
 * 退避策略
 */
public class RetryBackOffUtil {
	/**
	 * 无退避策略
	 * 
	 * @return
	 */
	public static BackOffPolicy getNoBackOffPolicy() {
		return new NoBackOffPolicy();
	}

	/**
	 * 设置固定退避策略
	 * 
	 * @return
	 */
	public static BackOffPolicy getFixedBackOffPolicy() {
		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		// 设置固定的延迟时间
		fixedBackOffPolicy.setBackOffPeriod(2000L);
		return fixedBackOffPolicy;
	}

	/**
	 * 设置随机退避策略，设置其最小时间和最大时间
	 * 
	 * @return
	 */
	public static BackOffPolicy getUniformRandomBackOffPolicy() {
		UniformRandomBackOffPolicy uniformRandomBackOffPolicy = new UniformRandomBackOffPolicy();
		// 设置最小延迟时间
		uniformRandomBackOffPolicy.setMinBackOffPeriod(500L);
		// 设置最大延迟时间
		uniformRandomBackOffPolicy.setMaxBackOffPeriod(2000L);
		return uniformRandomBackOffPolicy;
	}

	/**
	 * 指数退避策略
	 * 
	 * @return
	 */
	public static BackOffPolicy getExponentialBackOffPolicy() {
		ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
		// 设置初始延迟时间
		exponentialBackOffPolicy.setInitialInterval(300L);
		// 设置最大延迟时间
		exponentialBackOffPolicy.setMaxInterval(4000L);
		// 设置两次延迟时间的倍数
		exponentialBackOffPolicy.setMultiplier(3);
		return new NoBackOffPolicy();
	}

	/**
	 * 指数退避策略
	 * 
	 * @return
	 */
	public static BackOffPolicy getExponentialRandomBackOffPolicy() {
		ExponentialRandomBackOffPolicy exponentialRandomBackOffPolicy = new ExponentialRandomBackOffPolicy();
		// 设置初始延迟时间
		exponentialRandomBackOffPolicy.setInitialInterval(300L);
		// 设置最大延迟时间
		exponentialRandomBackOffPolicy.setMaxInterval(4000L);
		// 设置两次延迟时间的倍数
		exponentialRandomBackOffPolicy.setMultiplier(3);
		return new NoBackOffPolicy();
	}
}
