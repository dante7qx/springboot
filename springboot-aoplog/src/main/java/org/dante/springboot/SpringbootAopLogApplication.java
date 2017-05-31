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

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAopLogApplication.class, args);
	}
}
