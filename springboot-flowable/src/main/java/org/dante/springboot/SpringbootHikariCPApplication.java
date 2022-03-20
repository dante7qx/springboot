package org.dante.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dante.springboot.mybatis.mapper")
public class SpringbootHikariCPApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHikariCPApplication.class, args);
	}
}
