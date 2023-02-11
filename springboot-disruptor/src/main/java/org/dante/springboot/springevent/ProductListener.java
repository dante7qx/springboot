package org.dante.springboot.springevent;

import java.util.concurrent.TimeUnit;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.hutool.core.thread.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 @EventListener 来监听事件
 * 
 * @author dante
 *
 */
@Slf4j
@AllArgsConstructor
@Component
public class ProductListener {

	private final ProductNotifyService notifyService;

	/**
	 * 事件监听器
	 * 
	 * @Async("spiritExecutor") 添加此注解，表明异步处理
	 * 
	 * @param event
	 */
	@Order
	@EventListener(BizEvent.class)
	@Async("spiritExecutor")
	public void msgNotify(BizEvent<Product> event) {
		ThreadUtil.sleep(3, TimeUnit.SECONDS);
		log.info("监听事件 --> {}", event);
		Product product = (Product) event.getSource();
		notifyService.notify(product);
	}

}
