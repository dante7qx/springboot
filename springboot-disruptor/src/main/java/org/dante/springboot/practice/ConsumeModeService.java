package org.dante.springboot.practice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import lombok.Setter;

/**
 * 将发布和消费事件的逻辑写在一个抽象类里，但是具体如何消费事件并不在此类中实现，而是留给子类
 * 
 * @author dante
 *
 */
public abstract class ConsumeModeService {

	/**
	 * 环形缓冲区大小
	 */
	protected int BUFFER_SIZE = 16;

	protected Disruptor<StringEvent> disruptor;

	@Setter
	private StringEventProducer producer;

	/**
	 * 统计消息总数
	 */
	protected final AtomicLong eventCount = new AtomicLong();

	/**
	 * 这是辅助测试用的， 测试的时候，完成事件发布后，测试主线程就用这个countDownLatch开始等待，
	 * 在消费到指定的数量(countDownLatchGate)后，消费线程执行countDownLatch的countDown方法，
	 * 这样测试主线程就可以结束等待了
	 */
	private CountDownLatch countDownLatch;

	/**
	 * 这是辅助测试用的， 测试的时候，完成事件发布后，测试主线程就用这个countDownLatch开始等待，
	 * 在消费到指定的数量(countDownLatchGate)后，消费线程执行countDownLatch的countDown方法，
	 * 这样测试主线程就可以结束等待了
	 */
	private int countDownLatchGate;

	/**
	 * 准备一个匿名类，传给disruptor的事件处理类， 这样每次处理事件时，都会将已经处理事件的总数打印出来
	 */
	protected Consumer<?> eventCountPrinter = new Consumer<Object>() {
		@Override
		public void accept(Object o) {
			long count = eventCount.incrementAndGet();

			/**
			 * 这是辅助测试用的， 测试的时候，完成事件发布后，测试主线程就用这个countDownLatch开始等待，
			 * 在消费到指定的数量(countDownLatchGate)后，消费线程执行countDownLatch的countDown方法，
			 * 这样测试主线程就可以结束等待了
			 */
			if (null != countDownLatch && count >= countDownLatchGate) {
				countDownLatch.countDown();
			}
		}
	};

	/**
	 * 这是辅助测试用的， 测试的时候，完成事件发布后，测试主线程就用这个countDownLatch开始等待，
	 * 在消费到指定的数量(countDownLatchGate)后，消费线程执行countDownLatch的countDown方法，
	 * 这样测试主线程就可以结束等待了
	 * 
	 * @param countDownLatch
	 * @param countDownLatchGate
	 */
	public void setCountDown(CountDownLatch countDownLatch, int countDownLatchGate) {
		this.countDownLatch = countDownLatch;
		this.countDownLatchGate = countDownLatchGate;
	}

	/**
	 * 发布一个事件
	 * 
	 * @param value
	 * @return
	 */
	public void publish(String value) {
		producer.onData(value);
	}

	public void multiPublish(String value) throws Exception {
		throw new Exception("父类未实现此方法，请在子类中重写此方法后再调用");
	}
	
	/**
	 * 返回已经处理的任务总数
	 * 
	 * @return
	 */
	public long eventCount() {
		return eventCount.get();
	}

	/**
	 * 留给子类实现具体的事件消费逻辑
	 */
	protected abstract void disruptorOperate();

	@PostConstruct
	protected void init() {
		// 实例化
		disruptor = new Disruptor<>(new StringEventFactory(), 
				BUFFER_SIZE,
				new CustomizableThreadFactory("disruptor-event-handler-"),
				ProducerType.SINGLE,
				new BlockingWaitStrategy());

		// 留给子类实现具体的事件消费逻辑
		disruptorOperate();

		// 启动
		disruptor.start();

		// 生产者
		setProducer(new StringEventProducer(disruptor.getRingBuffer()));
	}

}
