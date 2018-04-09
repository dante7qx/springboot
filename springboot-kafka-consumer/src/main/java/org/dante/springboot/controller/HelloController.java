package org.dante.springboot.controller;

import java.util.UUID;

import org.dante.springboot.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class HelloController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/send")
	public void send() {
		kafkaTemplate.send("hello_kafka", UUID.randomUUID().toString(), "Hello Kafka, I am a producer!");
	}

	@GetMapping("/send2")
	public void send2() throws JsonProcessingException {
		kafkaTemplate.send("hello_pojo", UUID.randomUUID().toString(),
				mapper.writeValueAsString(new MessageVO(UUID.randomUUID().toString(), "我要被推送到Kafka里了，很不多！")));
	}

}
