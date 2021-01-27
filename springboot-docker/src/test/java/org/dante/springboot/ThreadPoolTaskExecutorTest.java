package org.dante.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 当我们向ThreadPoolTaskExecutor提交新任务时，
 * 1. 正在运行的线程少于corePoolSize线程（即使池中有空闲线程），它将创建一个新线程。
 * 2. 正在运行的线程少于maxPoolSize且由queueCapacity定义的队列已满，它将创建一个新线程。
 * 
 * @author dante
 *
 */
@Slf4j
public class ThreadPoolTaskExecutorTest {
	
	private static final int COUNTDOWN_LATCH = 20;
	private static final int NUM_THREADS = 20;

	/**
	 * 开始一个新的线程（countDownLatch = numThreads）
	 * 
	 * @param taskExecutor
	 * @param countDownLatch	线程计数器，一个线程执行完毕计数器减一
	 * @param numThreads		启动的线程数量
	 */
	public void startThreads(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch, int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			taskExecutor.execute(() -> {
				try {
					Thread.sleep(100L * ThreadLocalRandom.current().nextLong(1, 10));
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
		}
	}
	
	/**
	 * 测试ThreadPoolTaskExecutor的默认配置， 
	 * 	  corePoolSize = 1
	 * 	  maxPoolSize = Integer.MAX_VALUE
	 * 	  queueCapacity = Integer.MAX_VALUE
	 * 结果，我们希望无论启动多少任务，我们都只会运行一个线程：
	 * 
	 */
	@Test
	public void whenUsingDefaults_thenSingleThread() {
		long startTime = System.currentTimeMillis();
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.afterPropertiesSet();

	    CountDownLatch countDownLatch = new CountDownLatch(COUNTDOWN_LATCH);
	    this.startThreads(taskExecutor, countDownLatch, NUM_THREADS);

	    while (countDownLatch.getCount() > 0) {
	    	if(taskExecutor.getPoolSize() != 1) {
	    		log.info("验证失败，启动运行的线程多于1个。");
	    	}
	        assertEquals(1, taskExecutor.getPoolSize());
	    }
	    long endTime = System.currentTimeMillis();
	    log.info("执行耗时: {} 毫秒", endTime - startTime);
	}
	
	/**
	 * 设置 corePoolSize = 5，无论提交给ThreadPoolTaskExecutor的任务数量如何，我们都希望启动五个线程：
	 * 
	 */
	@Test
	public void whenCorePoolSizeFive_thenFiveThreads() {
		long startTime = System.currentTimeMillis();
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.setCorePoolSize(5);
	    taskExecutor.afterPropertiesSet();

	    CountDownLatch countDownLatch = new CountDownLatch(COUNTDOWN_LATCH);
	    this.startThreads(taskExecutor, countDownLatch, NUM_THREADS);

	    while (countDownLatch.getCount() > 0) {
	    	if(taskExecutor.getPoolSize() != 5) {
	    		log.info("验证失败，启动运行的线程多于5个。");
	    	}
	        assertEquals(5, taskExecutor.getPoolSize());
	    }
	    long endTime = System.currentTimeMillis();
	    log.info("执行耗时: {} 毫秒", endTime - startTime);
	}
	
	/**
	 * maxPoolSize = 10，corePoolSize = 5
	 * 仅启动五个线程。 需要说明的是，只有五个线程启动，因为queueCapacity仍然不受限制：
	 * 
	 */
	@Test
	public void whenCorePoolSizeFiveAndMaxPoolSizeTen_thenFiveThreads() {
		long startTime = System.currentTimeMillis();
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.setCorePoolSize(5);
	    taskExecutor.setMaxPoolSize(10);
	    taskExecutor.afterPropertiesSet();

	    CountDownLatch countDownLatch = new CountDownLatch(COUNTDOWN_LATCH);
	    this.startThreads(taskExecutor, countDownLatch, NUM_THREADS);

	    while (countDownLatch.getCount() > 0) {
	        assertEquals(5, taskExecutor.getPoolSize());
	    }
	    long endTime = System.currentTimeMillis();
	    log.info("执行耗时: {} 毫秒", endTime - startTime);
	}
	
	/**
	 * 设置 queueCapacity 的数量，当线程队列满了后，会创建一个新线程
	 * 
	 */
	@Test
	public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityTen_thenTenThreads() {
		long startTime = System.currentTimeMillis();
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.setCorePoolSize(5);
	    taskExecutor.setMaxPoolSize(10);
	    taskExecutor.setQueueCapacity(10);
	    taskExecutor.afterPropertiesSet();

	    CountDownLatch countDownLatch = new CountDownLatch(COUNTDOWN_LATCH);
	    this.startThreads(taskExecutor, countDownLatch, NUM_THREADS);

	    while (countDownLatch.getCount() > 0) {
	        assertEquals(10, taskExecutor.getPoolSize());
	    }
	    long endTime = System.currentTimeMillis();
	    log.info("执行耗时: {} 毫秒", endTime - startTime);
	}
}
