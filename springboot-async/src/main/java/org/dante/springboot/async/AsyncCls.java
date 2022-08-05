package org.dante.springboot.async;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务类
 * 
 * @author dante
 *
 */
@Slf4j
@Service
public class AsyncCls {
	
	@Async("spiritExecutor")
	public void sendMsg() {
		log.info("=======>开始发送消息。。。。。");
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
		log.info("=======>消息发送完成。。。。。");
	}
	
}
