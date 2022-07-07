package org.dante.springboot.mutex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.docker.mutex.GuavaMonitorSequenceGenerator;
import org.dante.springboot.docker.mutex.ReentrantLockSequenceGenerator;
import org.dante.springboot.docker.mutex.SemaphoreSequenceGenerator;
import org.dante.springboot.docker.mutex.SequenceGenerator;
import org.dante.springboot.docker.mutex.SynchronizedBlockSequenceGenerator;
import org.dante.springboot.docker.mutex.SynchronizedMethodSequenceGenerator;
import org.junit.jupiter.api.Test;

public class SequenceGeneratorTest {
	
	@Test
	public void unSafeSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new SequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	@Test
	public void synchronizedMethodSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new SynchronizedMethodSequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	@Test
	public void synchronizedBlockSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new SynchronizedBlockSequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	@Test
	public void reentrantLockSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new ReentrantLockSequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	@Test
	public void semaphoreSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new SemaphoreSequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	@Test
	public void guavaMonitorSequenceGenerator() throws Exception {
		int count = 10000;
	    Set<Integer> sequences = getUniqueSequences(new GuavaMonitorSequenceGenerator(), count);
	    assertEquals(count, sequences.size());
	}
	
	
	private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
	    ExecutorService executor = Executors.newFixedThreadPool(10);
	    Set<Integer> uniqueSequences = new LinkedHashSet<>();
	    List<Future<Integer>> futures = new ArrayList<>();

	    for (int i = 0; i < count; i++) {
	        futures.add(executor.submit(generator::getNextSequence));
	    }

	    for (Future<Integer> future : futures) {
	        uniqueSequences.add(future.get());
	    }

	    executor.awaitTermination(1, TimeUnit.SECONDS);
	    executor.shutdown();

	    return uniqueSequences;
	}
	
	
}
