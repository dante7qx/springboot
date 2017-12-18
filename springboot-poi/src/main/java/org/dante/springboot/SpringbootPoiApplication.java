package org.dante.springboot;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringbootPoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPoiApplication.class, args);
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/Users/dante/Documents/Project/spring/springboot/springboot-poi/src/main/resources/tmp");
		return factory.createMultipartConfig();
	}
}
