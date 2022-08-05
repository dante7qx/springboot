package org.dante.springboot.async;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 失效原因
 * 
 * 1.没有启用 @EnableAsync 注解。
 * 2.异步方法使用注解@Async的返回值只能为void或者Future。
 * 3.没有走Spring的代理类。因为@Transactional和@Async注解的实现都是基于Spring的AOP，而AOP的实现是基于动态代理模式（走接口）实现的。
 * 	 那么注解失效的原因就很明显了，有可能因为调用方法的是对象本身而不是代理对象，因为没有经过Spring容器。
 * 
 * 参考资料：https://cxybb.com/article/YoungLee16/88398045
 * 
 * @author dante
 *
 */
@Slf4j
@RestController
public class AysncTaskController {
	
	@Autowired
	private AsyncCls asyncCls;
	
	/**
	 * Async 注解失效
	 * 
	 * @return
	 */
	@GetMapping("/async")
	public String asyncDemo() {
		log.info("=======>主线程运行");
		sendMsg();
		log.info("=======>主线程结束");
		return "async-demo";
	}
	
	/**
	 * Async 注解启用（类的内部调用解决方式）
	 * 
	 * @return
	 */
	@GetMapping("/async1")
	public String asyncDemo1() {
		log.info("=======>主线程运行");
		AysncTaskController atc = SpringUtil.getBean(AysncTaskController.class);
		atc.sendMsg();
		log.info("=======>主线程结束");
		return "async-demo1";
	}
	
	/**
	 * Async 外部调用
	 * 
	 * @return
	 */
	@GetMapping("/async2")
	public String asyncDemo2() {
		log.info("=======>主线程运行");
		asyncCls.sendMsg();
		log.info("=======>主线程结束");
		return "async-demo2";
	}
	
	
	
	
	/**
	 * 异步方法
	 */
	@Async("spiritExecutor")
	public void sendMsg() {
		log.info("=======>开始发送消息。。。。。");
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
		log.info("=======>消息发送完成。。。。。");
	}
}
