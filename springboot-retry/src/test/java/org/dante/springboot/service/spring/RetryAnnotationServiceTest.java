package org.dante.springboot.service.spring;

import org.dante.springboot.SpringbootRetryApplicationTests;
import org.dante.springboot.service.RetryAnnotationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryAnnotationServiceTest extends SpringbootRetryApplicationTests {
	
	@Autowired
    private RetryAnnotationService retryAnnotationService;

    @Test
    public void baseRetry() throws Exception {
        int store = retryAnnotationService.baseRetry(-1);
        log.info("store -> {}", store);
    }

    /**
     * 其配置的Recover方法和重试方法返回值不同，会报错
     * @throws Exception
     */
    @Test
    public void baseRetryError() throws Exception {
        int store = retryAnnotationService.baseRetryError(-1);
        log.info("store -> {}", store);
    }
	
}	
