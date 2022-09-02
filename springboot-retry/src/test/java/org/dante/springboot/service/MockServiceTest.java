package org.dante.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;

import org.dante.springboot.vo.Response;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * https://blog.csdn.net/vipshop_fin_dev/article/details/118075504
 */
@Slf4j
public class MockServiceTest {
	
	@Test
	public void testRetry() {
		Response response = graceRetry();
		log.info("{}", response);
		assertEquals(200, response.getCode());
	}
	
	private Response graceRetry() {
        Retryer<Response> retryer = RetryerBuilder.<Response>newBuilder()
                .retryIfException()		// 当发生异常时重试
                .retryIfResult(response -> response.getCode() != 200)		// 当返回码不为200时重试
                .withWaitStrategy(WaitStrategies.fibonacciWait(1000, 10, TimeUnit.SECONDS))	// 等待策略：使用斐波拉契数列递增等待
                .withStopStrategy(StopStrategies.stopAfterAttempt(10))		// 重试达到10次时退出
                .build();
        try {
            return retryer.call(new Callable<Response>() {
                @Override
                public Response call() throws Exception {
                    log.info("重试调用");
                    return MockService.call();
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("重试失败");
    }
	
}
