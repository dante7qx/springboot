package org.dante.springboot.docker.mutex;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockSequenceGenerator extends SequenceGenerator {
	
	private ReentrantLock mutex = new ReentrantLock();
	
	@Override
	public int getNextSequence() {
		try {
			mutex.lock();
			return super.getNextSequence();
		} finally {
			mutex.unlock();
		}
	}
	
}
