package org.dante.springboot.practice.impl;

import javax.annotation.PostConstruct;

import org.dante.springboot.practice.ConsumeModeService;
import org.dante.springboot.practice.MailEventHandler;
import org.dante.springboot.practice.StringEventFactory;
import org.dante.springboot.practice.StringEventProducer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import lombok.Setter;

@Service("multiProducerService")
public class MultiProducerServiceImpl extends ConsumeModeService {
	
	/**
	 * 第二个生产者
	 */
	@Setter
	private StringEventProducer producer2;
	
	@Override
	@PostConstruct
	protected void init() {
		// 实例化
        disruptor = new Disruptor<>(new StringEventFactory(),
                BUFFER_SIZE,
                new CustomizableThreadFactory("disruptor-event-handler2-"),
                // 生产类型是多生产者
                ProducerType.MULTI,
                // BlockingWaitStrategy是默认的等待策略
                new BlockingWaitStrategy());

        // 留给子类实现具体的事件消费逻辑
        disruptorOperate();
        
	    // 启动
	    disruptor.start();
	
	    // 第一个生产者
	    setProducer(new StringEventProducer(disruptor.getRingBuffer()));
	
	    // 第二个生产者
	    setProducer2(new StringEventProducer(disruptor.getRingBuffer()));
	}
	
	@Override
	protected void disruptorOperate() {
		// 一号消费者
		MailEventHandler c1 = new MailEventHandler(eventCountPrinter);

		// 二号消费者
		MailEventHandler c2 = new MailEventHandler(eventCountPrinter);

		// 调用handleEventsWith，表示创建的多个消费者以独立消费的模式消费
		disruptor.handleEventsWith(c1, c2);
	}
	
	@Override
    public void multiPublish(String value) throws Exception {
        producer2.onData(value);
    }

}
