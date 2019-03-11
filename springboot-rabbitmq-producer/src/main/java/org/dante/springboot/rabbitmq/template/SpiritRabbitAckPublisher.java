package org.dante.springboot.rabbitmq.template;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpiritRabbitAckPublisher implements RabbitTemplate.ConfirmCallback {

	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public SpiritRabbitAckPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitTemplate.setConfirmCallback(this);  
	}
	
	/**
	 * 默认交换器 Direct Exchange
	 * 
	 * @param routingKey
	 * @param Message
	 */
	public void sendMessage(String routingKey, Object message) {
		rabbitTemplate.convertAndSend(routingKey, message, new CorrelationData(UUID.randomUUID().toString()));
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
	
	/**
	 * 发布者消息确认
	 * 一个RabbitTemplate只能支持一个 ConfirmCallback 处理，所以，RabbitTemplate 要使用Prototype
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if(ack) {
			log.info("消息 {} 已被接收。", correlationData.getId());
		} else {
			log.error("消息发送失败，原因：{}", cause);
		}
	}

}
