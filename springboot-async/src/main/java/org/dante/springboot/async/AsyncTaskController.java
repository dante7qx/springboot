package org.dante.springboot.async;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 失效原因
 * 1.没有启用 @EnableAsync 注解。
 * 2.异步方法使用注解@Async的返回值只能为void或者Future。
 * 3.没有走Spring的代理类。因为@Transactional和@Async注解的实现都是基于Spring的AOP，而AOP的实现是基于动态代理模式（走接口）实现的。
 * 	 那么注解失效的原因就很明显了，有可能因为调用方法的是对象本身而不是代理对象，因为没有经过Spring容器。
 * 参考资料：https://cxybb.com/article/YoungLee16/88398045
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncTaskController {
	
	private final AsyncCls asyncCls;

	/**
	 * Async 注解失效
	 */
	@GetMapping("/async")
	public String asyncDemo() {
		Console.log("=======>主线程运行");
		sendMsg();
		Console.log("=======>主线程结束");
		return "async-demo";
	}
	
	/**
	 * Async 注解启用（类的内部调用解决方式）
	 */
	@GetMapping("/async1")
	public String asyncDemo1() {
		log.info("=======>主线程运行");
		AsyncTaskController atc = SpringUtil.getBean(AsyncTaskController.class);
		atc.sendMsg();
		log.info("=======>主线程结束");
		return "async-demo1";
	}
	
	/**
	 * Async 外部调用
	 */
	@GetMapping("/async2")
	public String asyncDemo2() {
		log.info("=======>主线程运行");
		asyncCls.sendMsg();
		log.info("=======>主线程结束");
		return "async-demo2";
	}

	
	/**
	 * 异步方法 — 无效@Async
	 */
	@Async("spiritExecutor")
	public void sendMsg() {
		log.info("=======>开始发送消息。。。。。");
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
		log.info("=======>消息发送完成。。。。。");
	}

	@GetMapping("/stats")
	public void statsThread() {
		Map<Thread, StackTraceElement[]> allThreads = Thread.getAllStackTraces();
		allThreads.forEach((thread, stack) -> {
			Console.log(
					thread.isVirtual() ? "[VirtualThread] " : "[PlatformThread] " +
							thread.getName() + " - State: " + thread.getState()
			);
		});
		long virtualThreads = Thread.getAllStackTraces().keySet()
				.stream()
				.filter(Thread::isVirtual)
				.count();

		long platformThreads = Thread.getAllStackTraces().keySet()
				.stream()
				.filter(t -> !t.isVirtual())
				.count();

		Console.log("Virtual Threads: {}, Platform Threads: {}", virtualThreads, platformThreads);
	}
}
