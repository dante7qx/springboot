package org.dante.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dante.springboot.dao")
public class SpringbootDaMengApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDaMengApplication.class, args);
	}
}