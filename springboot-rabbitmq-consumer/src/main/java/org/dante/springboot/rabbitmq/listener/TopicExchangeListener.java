package org.dante.springboot.rabbitmq.listener;

import org.dante.springboot.rabbitmq.vo.PubMsg;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TopicExchangeListener {
	
	@RabbitListener(bindings=@QueueBinding(value=@Queue(autoDelete="true",ignoreDeclarationExceptions="true") ,exchange=@Exchange(value="${spirit.rabbitmq.topic-exchange}",durable="true",type=ExchangeTypes.TOPIC), key = "*.sbr.topic.routing.key.#"))
	public void receiveTopicMsg1(Object msg) {
		log.info("订阅者1收到消息 {}.", msg);
	}
	
	@RabbitListener(bindings=@QueueBinding(value=@Queue(autoDelete="true") ,exchange=@Exchange(value="${spirit.rabbitmq.topic-exchange}",durable="true",type=ExchangeTypes.TOPIC), key = "#.sbr.topic.routing.key.#"))
	public void receiveTopicMsg2(PubMsg msg) {
		log.info("订阅者2收到消息 {}.", msg);
	}
}
