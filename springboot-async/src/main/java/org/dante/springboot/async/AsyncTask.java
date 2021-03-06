package org.dante.springboot.async;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * 异步调用
 * 
 * 默认使用缺省的Executor，TaskExecutor
 * 
 * @author dante
 *
 */
@Component
public class AsyncTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTask.class);

	@Async
	public Future<String> doTask1() throws InterruptedException {
		LOGGER.info("Task1 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(5000);
		long end = System.currentTimeMillis();

		LOGGER.info("Task1 执行完成, 耗时: {} ms.", end - start);

		return new AsyncResult<>("Task1 执行完成!");
	}

	@Async
	public Future<String> doTask2() throws InterruptedException {
		LOGGER.info("Task2 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(3000);
		long end = System.currentTimeMillis();

		LOGGER.info("Task2 执行完成, 耗时: {} ms.", end - start);

		return new AsyncResult<>("Task2 执行完成!");
	}
	
	@Async("mySimpleAsync")
	public Future<String> doTask3() throws InterruptedException {
		LOGGER.info("Task3 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(5000);
		long end = System.currentTimeMillis();

		LOGGER.info("Task3 执行完成, 耗时: {} ms.", end - start);

		return new AsyncResult<>("Task3 执行完成!");
	}

	@Async("myAsync")
	public Future<String> doTask4() throws InterruptedException {
		LOGGER.info("Task4 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(3000);
		long end = System.currentTimeMillis();

		LOGGER.info("Task4 执行完成, 耗时: {} ms.", end - start);

		return new AsyncResult<>("Task4 执行完成!");
	}
	
	@Async
	public Future<String> doCancel() throws InterruptedException {
		LOGGER.info("CancelTask 开始执行...");
		long start = System.currentTimeMillis();
		int i = 0;
		while(i < 10) {
			Thread.sleep(100);	// 响应中断, 接收 cancel() 方法的请求
			i++;
		}
		long end = System.currentTimeMillis();
		LOGGER.info("CancelTask 执行完成, 耗时: {} ms.", end - start);

		return new AsyncResult<>("CancelTask 执行完成!");
	}
}
