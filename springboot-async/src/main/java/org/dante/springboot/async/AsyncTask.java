package org.dante.springboot.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 异步调用
 * 默认使用缺省的Executor，TaskExecutor
 * 
 * @author dante
 *
 */
@Slf4j
@Component
public class AsyncTask {
	
	@Async("spiritExecutor")
	public Future<String> doTask1() throws InterruptedException {
		log.info("Task1 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(5000);
		long end = System.currentTimeMillis();

		log.info("Task1 执行完成, 耗时: {} ms.", end - start);

		return CompletableFuture.completedFuture("Task1 执行完成!");
	}

	@Async("spiritExecutor")
	public Future<String> doTask2() throws InterruptedException {
		log.info("Task2 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(3000);
		long end = System.currentTimeMillis();

		log.info("Task2 执行完成, 耗时: {} ms.", end - start);

		return CompletableFuture.completedFuture("Task2 执行完成!");
	}
	
	@Async("mySimpleAsync")
	public Future<String> doTask3() throws InterruptedException {
		log.info("Task3 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(5000);
		long end = System.currentTimeMillis();

		log.info("Task3 执行完成, 耗时: {} ms.", end - start);

		return CompletableFuture.completedFuture("Task3 执行完成!");
	}

	@Async("spiritExecutor")
	public Future<String> doTask4() throws InterruptedException {
		log.info("Task4 开始执行...");
		long start = System.currentTimeMillis();
		Thread.sleep(3000);
		long end = System.currentTimeMillis();

		log.info("Task4 执行完成, 耗时: {} ms.", end - start);

		return CompletableFuture.completedFuture("Task4 执行完成!");
	}
	
	@Async("spiritExecutor")
	public Future<String> doCancel() throws InterruptedException {
		log.info("CancelTask 开始执行...");
		long start = System.currentTimeMillis();
		int i = 0;
		int count = 10;
		int sleep = 100;
		while(i < count) {
			// 响应中断, 接收 cancel() 方法的请求
			Thread.sleep(sleep);	
			i++;
		}
		long end = System.currentTimeMillis();
		log.info("CancelTask 执行完成, 耗时: {} ms.", end - start);

		return CompletableFuture.completedFuture("CancelTask 执行完成!");
	}
	
}
