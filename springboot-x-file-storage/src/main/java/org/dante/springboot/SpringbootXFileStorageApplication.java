package org.dante.springboot;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFileStorage
@SpringBootApplication
public class SpringbootXFileStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootXFileStorageApplication.class, args);
	}
}
