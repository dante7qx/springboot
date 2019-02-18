package org.dante.springboot;

import org.dante.springboot.dao.PersonDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringbootFlywayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFlywayApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(PersonDAO repository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				log.error("Persons -> {}", repository.findAll());
			}

		};
	}

}
