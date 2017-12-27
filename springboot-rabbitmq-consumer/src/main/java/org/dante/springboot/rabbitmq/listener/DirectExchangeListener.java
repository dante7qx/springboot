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
public class DirectExchangeListener {

	@RabbitListener(queues = "${spirit.rabbitmq.default-queue}")
	public void receiveMessage1(PubMsg msg) {
		log.info("DirectExchangeListener 收到消息1 {}", msg);
	}
	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(ignoreDeclarationExceptions="true"),
			exchange=@Exchange(value="${spirit.rabbitmq.direct-exchange}", durable="true", type=ExchangeTypes.DIRECT),
			key="${spirit.rabbitmq.direct-routing-key}"))
	public void receiveMessage2(PubMsg msg) {
		log.info("DirectExchangeListener 收到消息2 {}", msg);
	}
}
