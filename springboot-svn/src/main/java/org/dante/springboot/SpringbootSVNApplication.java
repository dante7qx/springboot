package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootSVNApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSVNApplication.class, args);
	}
}
