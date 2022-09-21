package org.dante.springboot.config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.gobrs.async.threadpool.GobrsAsyncThreadPoolFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义线程池
 * 
 * Gobrs-Async 默认使用的是 Executors.newCachedThreadPool() 的线程池。
 * 想自定义线程池。满足自己的线程池需求。需要 GobrsAsyncThreadPoolFactory 对象。
 * 
 */
@Configuration
public class ThreadpoolConfig {

	@Autowired
	private GobrsAsyncThreadPoolFactory factory;

	@PostConstruct
	public void gobrsThreadPoolExecutor() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(300, 500, 30, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		factory.setThreadPoolExecutor(threadPoolExecutor);
	}
}
