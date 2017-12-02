package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用AOP统一处理Web日志
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootAopLogApplication {
	
	/*
	@Bean
    public FilterRegistrationBean signValidateFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CacheInputStreamFilter cacheInputStreamFilter = new CacheInputStreamFilter();
        registration.setFilter(cacheInputStreamFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(-11);
        return registration;
    }
    */

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAopLogApplication.class, args);
	}
	
}
