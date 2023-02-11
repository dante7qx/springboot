package org.dante.springboot.eventbus;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventBusListener {

	@Autowired
	private GoodsNotifyService notifyService;

	@Subscribe
	public void listenEvent(Object event) throws Exception {
		ThreadUtil.sleep(3, TimeUnit.SECONDS);
		log.info("监听事件 --> {}", event);
		notifyService.notify((Goods) event);
	}

}
