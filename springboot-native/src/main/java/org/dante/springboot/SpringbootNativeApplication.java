package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootNativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootNativeApplication.class, args);
	}
	
	@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}
}
