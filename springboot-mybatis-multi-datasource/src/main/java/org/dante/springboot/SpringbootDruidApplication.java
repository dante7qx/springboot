package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootDruidApplication {

	// TODO: 从数据源中 Mybatis 的事务不生效
	public static void main(String[] args) {
		SpringApplication.run(SpringbootDruidApplication.class, args);
	}
}
