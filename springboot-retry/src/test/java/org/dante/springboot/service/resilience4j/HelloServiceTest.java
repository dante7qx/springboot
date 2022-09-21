package org.dante.springboot.service.resilience4j;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;

import org.dante.springboot.SpringbootRetryApplicationTests;
import org.dante.springboot.service.HelloService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.io.watch.WatchException;
import io.github.resilience4j.retry.Retry;
import io.vavr.control.Try;

/**
 * https://www.codercto.com/a/44253.html
 * 
 */
public class HelloServiceTest extends SpringbootRetryApplicationTests {

	@Autowired
	private HelloService helloService;
	
	@Test
	public void shouldNotRetry() {
		// 默认的重试类
		Retry retryContext = Retry.ofDefaults("id");
		
		// 装饰 MockService 的调用
		Runnable runnable = Retry.decorateRunnable(retryContext, helloService::call);
		runnable.run();
		
		// HelloService 调用了一次
		then(helloService).should(Mockito.times(1)).call();
	}
	
	@Test
	public void testDecorateRunnable() {
		// Given the HelloWorldService throws an exception
		willThrow(new WatchException("BAM!")).given(helloService).call();

		// Create a Retry with default configuration
		Retry retry = Retry.ofDefaults("id");
		// Decorate the invocation of the HelloWorldService
		Runnable runnable = Retry.decorateRunnable(retry, helloService::call);

		// When
		Try<Void> result = Try.run(runnable::run);

		// Then the helloWorldService should be invoked 3 times
		then(helloService).should(Mockito.times(3)).call();
		// and the result should be a failure
		Assertions.assertTrue(result.isFailure());
		// and the returned exception should be of type RuntimeException
		Assertions.assertTrue(result.failed().get() instanceof WatchException);
	}

	@Test
	public void testExecuteRunnable() {
		// Create a Retry with default configuration
		Retry retry = Retry.ofDefaults("id");
		// Decorate the invocation of the HelloWorldService
		retry.executeRunnable(helloService::call);

		// Then the helloWorldService should be invoked 1 time
		then(helloService).should(Mockito.times(2)).call();
	}

}
