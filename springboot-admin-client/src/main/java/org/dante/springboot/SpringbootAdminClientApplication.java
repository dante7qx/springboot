package org.dante.springboot;

import org.dante.springboot.endpoint.ServerEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAdminClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminClientApplication.class, args);
	}
	
	@Bean
	public Endpoint<String> serverEndpoint() {
		Endpoint<String> serverEndpoint = new ServerEndpoint("spirit");
		return serverEndpoint;
	}
}
