package org.dante.springboot.data.service;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 双端队列
 * 
 * 参考资料: https://www.cnblogs.com/wk-missQ1/p/16010952.html
 * 
 * @author dante
 *
 */
@Slf4j
@Service
public class QueueService {

	@Autowired
	private RedissonClient redissonClient;

	private static final String REDIS_MQ = "redisMQ";

	/**
	 * 发送消息到队列头部
	 *
	 * @param message
	 */
	public void sendMessage(String message) {
		RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(REDIS_MQ);

		try {
			blockingDeque.putFirst(message);
			log.info("将消息: {} 插入到队列。", message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从队列尾部阻塞读取消息，若没有消息，线程就会阻塞等待新消息插入，防止 CPU 空转
	 */
	public void onMessage() {
		RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(REDIS_MQ);
		while (true) {
			try {
				String message = blockingDeque.takeLast();
				log.info("从队列 {} 中读取到消息：{}.", REDIS_MQ, message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
