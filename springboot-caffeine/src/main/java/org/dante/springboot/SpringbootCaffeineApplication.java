package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootCaffeineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCaffeineApplication.class, args);
	}
}
