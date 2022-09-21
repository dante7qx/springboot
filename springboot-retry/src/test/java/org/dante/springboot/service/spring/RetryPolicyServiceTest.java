package org.dante.springboot.service.spring;

import org.dante.springboot.SpringbootRetryApplicationTests;
import org.dante.springboot.service.RetryPolicyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RetryPolicyServiceTest extends SpringbootRetryApplicationTests {
	
	@Autowired
    private RetryPolicyService retryPolicyService;

    @Test
    public void baseRetry() throws Throwable {
        retryPolicyService.baseRetryTemplate(1);
    }

	
}
