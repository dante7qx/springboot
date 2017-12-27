package org.dante.springboot;

import org.dante.springboot.rabbitmq.prop.SpiritRabbitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SpiritRabbitProperties.class)
@SpringBootApplication
public class SpringbootRabbitMQApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitMQApplication.class, args);
	}
}
