package org.dante.springboot.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.SpringbootAsyncApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncTaskTests extends SpringbootAsyncApplicationTests {

	@Autowired
	private AsyncTask asyncTask;

	Map<String, Future<String>> map = new HashMap<String, Future<String>>();

	@Test
	public void executeTask() {

		try {
			Future<String> task1 = asyncTask.doTask1();
			map.put("a", task1);
			Future<String> task2 = map.get("a");
			for (;;) {
				log.info("任务执行状态: {}", task2.isDone());
				if (task2.isDone()) {
					log.info("任务执行结果: {}", task2.get());
					break;
				}
				TimeUnit.SECONDS.sleep(1L);
			}
			log.info("All tasks finished.");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	public void cancelTask() {
		try {
			Future<String> ct = asyncTask.doCancel();
			if (!ct.isDone()) {
				ct.cancel(true);
			}
			log.info("All tasks finished.");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Test
	public void asyncTaskTest() throws InterruptedException, ExecutionException {
		Future<String> task1 = asyncTask.doTask1();
		Future<String> task2 = asyncTask.doTask2();

		while (true) {
			if (task1.isDone() && task2.isDone()) {
				log.info("Task1 result: {}", task1.get());
				log.info("Task2 result: {}", task2.get());
				break;
			}
			Thread.sleep(1000);
		}

		log.info("All tasks finished.");
	}

	@Test
	public void asyncTaskExecutorTest() throws InterruptedException, ExecutionException {
		Future<String> task3 = asyncTask.doTask3();
		Future<String> task4 = asyncTask.doTask4();

		while (true) {
			if (task3.isDone() && task4.isDone()) {
				log.info("Task3 result: {}", task3.get());
				log.info("Task4 result: {}", task4.get());
				break;
			}
			Thread.sleep(1000);
		}

		log.info("All tasks finished.");
	}
}
