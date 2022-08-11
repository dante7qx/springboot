package org.dante.springboot.service;

import org.dante.springboot.SpringbootRetryApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryServiceTest extends SpringbootRetryApplicationTests {
	
	@Autowired
    private RetryService retryService;

    @Test
    public void baseRetry() throws Exception {
        int store = retryService.baseRetry(-1);
        log.info("Store -> {}", store);
    }

    /**
     * 因为使用AOP 所以在同一个类中方法调用重试方法是无效的
     * @throws Exception
     */
    @Test
    public void baseRetryError() throws Exception {
        int store = retryService.baseRetryError(-1);
        log.info("Store -> {}", store);
    }

    /**
     * 因为使用AOP 使用静态方法无效
     * @throws Exception
     */
	@Test
	@SuppressWarnings("static-access")
    public void baseRetryStatic() throws Exception {
        int store = retryService.baseRetryStatic(-1);
        log.info("Store -> {}", store);
    }
	
}
