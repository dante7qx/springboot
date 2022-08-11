package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

// 开启重试的支持，只是注解方式进行重试的时候需要
@EnableRetry
@SpringBootApplication
public class SpringbootRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRetryApplication.class, args);
	}
}
