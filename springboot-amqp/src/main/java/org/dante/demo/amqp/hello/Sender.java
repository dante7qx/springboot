package org.dante.demo.amqp.hello;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.dante.demo.amqp.dto.req.DataDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(DataDto dto) {
		rabbitTemplate.convertAndSend(RabbitMQConstant.HELLO_QUEUE, dto);
		rabbitTemplate.convertAndSend(RabbitMQConstant.CONTAINER_QUEUE, dto);
		
		rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_ROUTING_KEY, dto);
		rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_NOT_DECLARE_QUEUE_ROUTING_KEY, dto);
		
		rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_EXCHANGE, "www" + RabbitMQConstant.TOPIC_ROUTING_KEY + "log", dto);
		rabbitTemplate.convertAndSend(RabbitMQConstant.TOPIC_EXCHANGE, "www" + RabbitMQConstant.TOPIC_ROUTING_KEY + "error.log", dto);
	
		rabbitTemplate.convertAndSend(RabbitMQConstant.FANOUT_EXCHANGE, null, dto);
	}
	
}
