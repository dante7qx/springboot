package org.dante.springboot.docker.mutex;

import com.google.common.util.concurrent.Monitor;

/**
 * Google 的 Guava 库的 Monitor 类是 ReentrantLock 类的更好替代品
 * 
 * @author dante
 *
 */
public class GuavaMonitorSequenceGenerator extends SequenceGenerator {

	private Monitor mutex = new Monitor();

	@Override
	public int getNextSequence() {
		mutex.enter();
		try {
			return super.getNextSequence();
		} finally {
			mutex.leave();
		}
	}

}
