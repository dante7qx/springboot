package org.dante.springboot.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考：
 * 	1. http://blog.csdn.net/z_alvin/article/details/77619907
 *  2. https://www.baeldung.com/spring-data-mongodb-reactive
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootMongoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongoApplication.class, args);
	}
	
	
}
