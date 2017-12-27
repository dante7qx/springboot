package org.dante.springboot.rabbitmq.template;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpiritRabbitPubilsher {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 默认交换器 Direct Exchange
	 * 
	 * @param routingKey
	 * @param Message
	 */
	public void sendMessage(String routingKey, Object message) {
		rabbitTemplate.convertAndSend(routingKey, message);
	}
 
	/**
	 * 指定交换器、路由键发布消息
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	public void sendMessage(String exchange, String routingKey, Object message) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(UUID.randomUUID().toString()));
	}
	
}
