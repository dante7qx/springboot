package org.dante.springboot;

import org.dante.springboot.prop.SpiritProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SpiritProperties.class)
public class SpringbootLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoggerApplication.class, args);
	}
}
