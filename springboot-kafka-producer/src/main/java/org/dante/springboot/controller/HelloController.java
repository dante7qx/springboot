package org.dante.springboot.controller;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.dante.springboot.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/send")
	public void send() throws InterruptedException, ExecutionException {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("p_hello", "Hello Kafka, I am a producer!");
		while(true) {
			log.info("消息发送中---> {}.", future.isDone());
			if(future.isDone()) {
				SendResult<String, String> sendResult = future.get();
				log.info("消息发送成功。{}, {}", sendResult.getProducerRecord(), sendResult.getRecordMetadata());
				break;
			}
			Thread.sleep(5000L);
			
		}
	}

	@GetMapping("/send2")
	public void send2() throws JsonProcessingException {
		kafkaTemplate.send("p_msg", mapper.writeValueAsString(new MessageVO(UUID.randomUUID().toString(), "我要被推送到Kafka里了，很不多！")));
	}

}
