package org.dante.springboot.kafka.service;

import org.dante.springboot.kafka.vo.PubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	/**
	 * 发送消息到kafka
	 * 
	 * @param topic   主题
	 * @param message 内容体
	 */
	public void sendMsg(String topic, PubMsg message) {
		kafkaTemplate.send(topic, message);
	}

}
