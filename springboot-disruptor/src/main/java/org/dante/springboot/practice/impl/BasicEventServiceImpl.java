package org.dante.springboot.practice.impl;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.dante.springboot.practice.BasicEventService;
import org.dante.springboot.practice.IBizService;
import org.dante.springboot.practice.StringEvent;
import org.dante.springboot.practice.StringEventExceptionHandler;
import org.dante.springboot.practice.StringEventFactory;
import org.dante.springboot.practice.StringEventHandler;
import org.dante.springboot.practice.StringEventProducer;
import org.dante.springboot.practice.StringEventProducerWithTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BasicEventServiceImpl implements BasicEventService {

	private static final int BUFFER_SIZE = 16;

	private Disruptor<StringEvent> disruptor;

	private StringEventProducer producer;
	private StringEventProducerWithTranslator producerWithTranslator;
	
	@Autowired
	private IBizService bizService;

	/**
	 * 统计消息总数
	 */
	private final AtomicLong eventCount = new AtomicLong();

	@PostConstruct
	private void init() {
		// 实例化
//		disruptor = new Disruptor<>(new StringEventFactory(), BUFFER_SIZE, new CustomizableThreadFactory("event-handler-"));
		disruptor = new Disruptor<StringEvent>(StringEvent::new, BUFFER_SIZE, new CustomizableThreadFactory("event-handler-"));	// Lambda风格

		// 准备一个匿名类，传给disruptor的事件处理类，
		// 这样每次处理事件时，都会将已经处理事件的总数打印出来
		Consumer<?> eventCountPrinter = new Consumer<Object>() {
			@Override
			public void accept(Object o) {
				long count = eventCount.incrementAndGet();
				log.info("接受 [{}] 事件消息", count);
			}
		};

		// 指定处理类
		disruptor.handleEventsWith(new StringEventHandler(eventCountPrinter));
		
		// Lambda风格
		/**
		disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            log.info("lambda操作, sequence [{}], endOfBatch [{}], event : {}", sequence, endOfBatch, event);
            // 这里延时100ms，模拟消费事件的逻辑的耗时
            Thread.sleep(100);
            // 计数
            eventCountPrinter.accept(null);
        });
        */
		
		// 设置全局的异常处理器类
		disruptor.setDefaultExceptionHandler(new StringEventExceptionHandler(bizService));

		// 启动
		disruptor.start();

		// 生产者
		producer = new StringEventProducer(disruptor.getRingBuffer());
		producerWithTranslator = new StringEventProducerWithTranslator(disruptor.getRingBuffer());
	}

	@Override
	public void publish(String value) {
//		producer.onData(value);
		producerWithTranslator.onData(value);
	}

	@Override
	public long eventCount() {
		return eventCount.get();
	}
}
