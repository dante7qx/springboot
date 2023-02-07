package org.dante.springboot.practice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;

import org.dante.springboot.SpringbootDisruptorApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumeModeServiceTest extends SpringbootDisruptorApplicationTests {
	
	/** 独立消费者数量 */
	public static final int INDEPENDENT_CONSUMER_NUM = 2;

	@Autowired
	@Qualifier("independentModeService")
	private ConsumeModeService independentModeService;

	@Autowired
	@Qualifier("shareModeService")
	private ConsumeModeService shareModeService;

	@Autowired
	@Qualifier("independentAndShareModeService")
	private ConsumeModeService independentAndShareModeService;

	@Autowired
	@Qualifier("multiProducerService")
	private ConsumeModeService multiProducerService;

	@Autowired
	@Qualifier("combineUseService")
	private ConsumeModeService combineUseService;

	/**
	 * 测试时生产的消息数量
	 */
	private static final int EVENT_COUNT = 20;

	@Test
	public void testIndependentModeService() throws InterruptedException {
		log.info("开始测试独立消费=========================================");
		testConsumeModeService(independentModeService, EVENT_COUNT,
				EVENT_COUNT * INDEPENDENT_CONSUMER_NUM);
	}

	@Test
	public void testShareModeService() throws InterruptedException {
		log.info("开始测试共同消费=========================================");
		testConsumeModeService(shareModeService, EVENT_COUNT, EVENT_COUNT);
	}

	@Test
	public void independentAndShareModeService() throws InterruptedException {
		log.info("开始测试独立消费和共同消费=========================================");
		testConsumeModeService(independentAndShareModeService, EVENT_COUNT,
				EVENT_COUNT * INDEPENDENT_CONSUMER_NUM);
	}

	@Test
	public void testMultiProducerService() throws InterruptedException {
		log.info("开始测试多个生产者发布消息=========================================");
		CountDownLatch countDownLatch = new CountDownLatch(1);

		// 两个生产者，每个生产100个事件，一共生产两百个事件
		// 两个独立消费者，每人消费200个事件，因此一共消费400个事件
		int expectEventCount = EVENT_COUNT * 4;

		// 告诉service，等消费到400个消息时，就执行countDownLatch.countDown方法
		multiProducerService.setCountDown(countDownLatch, expectEventCount);

		// 启动一个线程，用第一个生产者生产事件
		new Thread(() -> {
			for (int i = 0; i < EVENT_COUNT; i++) {
				log.info("生产者1【 {} 】", i);
				multiProducerService.publish(String.valueOf(i));
			}
		}).start();

		// 再启动一个线程，用第二个生产者生产事件
		new Thread(() -> {
			for (int i = 0; i < EVENT_COUNT; i++) {
				log.info("生产者2【 {} 】", i);
				try {
					multiProducerService.multiPublish(String.valueOf(i));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		// 当前线程开始等待，前面的service.setCountDown方法已经告诉过service，
		// 等消费到expectEventCount个消息时，就执行countDownLatch.countDown方法
		// 千万注意，要调用await方法，而不是wait方法！
		countDownLatch.await();

		// 消费的事件总数应该等于发布的事件数
		assertEquals(expectEventCount, multiProducerService.eventCount());
	}

	@Test
	public void testCombineUseService() throws InterruptedException {
		log.info("开始测试独立消费和共同消费=========================================");
		testConsumeModeService(combineUseService, EVENT_COUNT,
				EVENT_COUNT + EVENT_COUNT * 2 + EVENT_COUNT * 2 + EVENT_COUNT);
	}

	private void testConsumeModeService(ConsumeModeService service, int eventCount, int expectEventCount)
			throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);

		// 告诉service，等消费到expectEventCount个消息时，就执行countDownLatch.countDown方法
		service.setCountDown(countDownLatch, expectEventCount);

		for (int i = 0; i < eventCount; i++) {
			service.publish(String.valueOf(i));
		}

		// 当前线程开始等待，前面的service.setCountDown方法已经告诉过service，
		// 等消费到expectEventCount个消息时，就执行countDownLatch.countDown方法
		// 千万注意，要调用await方法，而不是wait方法！
		countDownLatch.await();

		// 消费的事件总数应该等于发布的事件数
		assertEquals(expectEventCount, service.eventCount());
	}
	
	@Test
	public void testBufSize() {
		Integer ringBufferSize = 4096 << 1 << 1;
		Integer consumerSize = Runtime.getRuntime().availableProcessors() << 1;
		System.out.println(ringBufferSize + " <==> " + consumerSize);
	}

}
