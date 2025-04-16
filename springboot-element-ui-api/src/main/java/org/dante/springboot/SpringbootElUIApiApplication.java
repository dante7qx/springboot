package org.dante.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.dante.springboot.mapper")
public class SpringbootElUIApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootElUIApiApplication.class, args);
	}
}
