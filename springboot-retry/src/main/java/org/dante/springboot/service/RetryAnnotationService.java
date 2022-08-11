package org.dante.springboot.service;

import java.time.LocalTime;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 快速启动，使用注解来进行重试
 */
@Slf4j
@Service
public class RetryAnnotationService {

	private final int totalNum = 100000;

	/**
	 * 测试业务支持重试
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	@Retryable(value = Exception.class, // 指定异常进行充数
			include = {}, // 处理异常
			exclude = {}, // 例外异常
			maxAttempts = 3, // 最大重试次数
			backoff = @Backoff( // 重试等待策略
					delay = 2000L, // 重试间隔
					multiplier = 1.5// 多次重试间隔系数2 、3、4.5
			), recover = "baseRetryRecover")
	public int baseRetry(Integer num) throws Exception {
		log.info("开始执行业务" + LocalTime.now());
		try {
			return 1 / 0;
		} catch (Exception e) {
			log.error("执行任务失败");
		}
		if (num <= 0) {
			throw new IllegalArgumentException("执行任务失败");
		}
		log.info("业务执行结束" + LocalTime.now());
		return totalNum - num;
	}

	/**
	 * 重试失败后的兜底方法
	 * 
	 * @param e
	 * @return
	 */
	@Recover
	public int baseRetryRecover(Exception e, Integer num) {
		log.warn("业务baseRetry执行失败！！！" + LocalTime.now());
		return totalNum;
	}

	/**
	 * 测试业务支持重试 其配置的Recover方法和重试方法返回值不同
	 * 
	 * @param num
	 * @return
	 * @throws Exception
	 */
	@Retryable(value = Exception.class, // 指定异常进行充数
			include = {}, // 处理异常
			exclude = {}, // 例外异常
			maxAttempts = 3, // 最大重试次数
			backoff = @Backoff( // 重试等待策略
					delay = 2000L, // 重试间隔
					multiplier = 1.5// 多次重试间隔系数2 、3、4.5
			), recover = "baseRetryRecover2")
	public int baseRetryError(Integer num) throws Exception {
		log.info("开始执行业务" + LocalTime.now());
		try {
			return 1 / 0;
		} catch (Exception e) {
			log.error("执行任务失败");
		}
		if (num <= 0) {
			throw new IllegalArgumentException("执行任务失败");
		}
		log.info("业务执行结束" + LocalTime.now());
		return totalNum - num;
	}

	/**
	 * 重试失败后的兜底方法
	 * 
	 * @param e
	 * @return
	 */
	@Recover
	public String baseRetryRecover2(Exception e) {
		log.warn("业务执行失败！！！" + LocalTime.now());
		return "业务执行失败";
	}

}
