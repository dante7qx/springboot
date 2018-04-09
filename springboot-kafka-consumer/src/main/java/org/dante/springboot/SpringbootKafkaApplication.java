package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringbootKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}
	
	@KafkaListener(topics = "p_msg")
    public void receive(String payload) {
        log.info("收到消息：{}", payload);
    }
}
