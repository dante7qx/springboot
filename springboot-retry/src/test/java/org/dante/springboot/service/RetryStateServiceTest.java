package org.dante.springboot.service;

import org.dante.springboot.SpringbootRetryApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RetryStateServiceTest extends SpringbootRetryApplicationTests {

	@Autowired
	private RetryStateService retryStateService;

	/**
	 * 有状态的重试，此时5次重试后熔断，重试时间小于openTimeout
	 * 
	 * @throws Exception
	 */
	@Test
	public void baseRetry() throws Exception {
		retryStateService.retryState(1500L * 1);
	}

	/**
	 * 有状态的重试，后熔断，重试时间大于openTimeout
	 * 
	 * @throws Exception
	 */
	@Test
	public void baseRetry2() throws Exception {
		retryStateService.retryState(2100L);
	}

	/**
	 * 有状态的重试，此时5次重试后熔断，重试时间小于openTimeout
	 * 
	 * @throws Exception
	 */
	@Test
	public void baseAnnotationRetry() throws Exception {
		for (int i = 0; i < 10; i++) {
			retryStateService.retryAnnotationState();
			if (i >= 6) {
				Thread.sleep(1000L * 1);
			}
		}
	}

	/**
	 * 有状态的重试，此时5次重试后熔断，重试时间大于openTimeout
	 * 
	 * @throws Exception
	 */
	@Test
	public void baseAnnotationRetry2() throws Exception {
		for (int i = 0; i < 10; i++) {
			retryStateService.retryAnnotationState();
			if (i >= 6) {
				Thread.sleep(3500L * 1);
			}
		}

	}

}
