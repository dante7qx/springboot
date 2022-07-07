package org.dante.springboot.docker.mutex;

/**
 * 同步方法
 * 
 * @author dante
 *
 */
public class SynchronizedMethodSequenceGenerator extends SequenceGenerator {
	
	@Override
	public synchronized int getNextSequence() {
		// TODO Auto-generated method stub
		return super.getNextSequence();
	}
	
	
}
