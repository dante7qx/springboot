package org.dante.springboot.docker.mutex;

import java.util.concurrent.Semaphore;

/**
 * 使用信号量
 * 
 * @author dante
 *
 */
public class SemaphoreSequenceGenerator extends SequenceGenerator {
	
	private Semaphore mutex = new Semaphore(1);
	
	@Override
	public int getNextSequence() {
		int seq = 0;
		try {
			mutex.acquire();
			seq = super.getNextSequence();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
		return seq;
		
	}
	
}
