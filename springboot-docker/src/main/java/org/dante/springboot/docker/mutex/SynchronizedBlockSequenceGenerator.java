package org.dante.springboot.docker.mutex;

/**
 * 同步代码块
 * 
 * @author dante
 *
 */
public class SynchronizedBlockSequenceGenerator extends SequenceGenerator {

	private Object mutex = new Object();

	@Override
	public int getNextSequence() {
		synchronized (mutex) {
			return super.getNextSequence();
		}
	}

}
