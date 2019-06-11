package org.dante.springboot;

import org.dante.springboot.tomcatlog.MsgPropValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringbootTomcatLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTomcatLogApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Validator configurationPropertiesValidator() {
		return new MsgPropValidator();
	}
}
