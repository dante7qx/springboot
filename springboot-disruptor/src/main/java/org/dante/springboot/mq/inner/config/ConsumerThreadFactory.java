package org.dante.springboot.mq.inner.config;

import java.util.concurrent.ThreadFactory;

/**
 * 消费者线程工厂
 * 
 * @author dante
 *
 */
public class ConsumerThreadFactory implements ThreadFactory {

	private String workName;

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread worker = new Thread(runnable, "Risun-inner-consumer-thread-" + workName);
        worker.setDaemon(true);
        return worker;
	}

}
