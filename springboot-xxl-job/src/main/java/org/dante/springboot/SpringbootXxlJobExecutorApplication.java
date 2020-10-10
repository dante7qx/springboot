package org.dante.springboot;

import org.dante.springboot.config.XxlJobProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(XxlJobProperties.class)
@SpringBootApplication
public class SpringbootXxlJobExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootXxlJobExecutorApplication.class, args);
	}
}
