package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = { MongoAutoConfiguration.class })
@SpringBootApplication
public class SpringbootJimuReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJimuReportApplication.class, args);
	}
}