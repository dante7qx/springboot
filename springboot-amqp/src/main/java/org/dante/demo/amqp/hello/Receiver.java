package org.dante.demo.amqp.hello;

import org.dante.demo.amqp.annotation.DanteTopicListener;
import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.dante.demo.amqp.dto.req.DataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	private final static Logger logger = LoggerFactory.getLogger(Receiver.class);
	
//	@RabbitListener(queues = { RabbitMQConstant.HELLO_QUEUE })
	@RabbitListener(queues = { "${dante.hello.queue}" })
	public void receiveMessage(DataDto dto) {
		logger.info("基本Queue收到消息：========================> " + dto);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitMQConstant.DIRECT_QUEUE, durable = "true"), exchange = @Exchange(value = RabbitMQConstant.DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable = "true", ignoreDeclarationExceptions = "true"), key = RabbitMQConstant.DIRECT_ROUTING_KEY))
	public void receiveDirectExchangeMessage(DataDto dto) {
		logger.info("Direct Exchange 收到消息：========================> " + dto);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(durable="true"), exchange = @Exchange(value = RabbitMQConstant.DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT, durable="true", ignoreDeclarationExceptions = "true"), key = RabbitMQConstant.DIRECT_NOT_DECLARE_QUEUE_ROUTING_KEY))
	public void receiveDirectExchangeNotDeclareQueueMessage(DataDto dto) {
		logger.info("Direct Exchange 未指定Queue收到消息：========================> " + dto);
	}

	@RabbitListener(bindings = @QueueBinding(value = @Queue(durable="true"), exchange = @Exchange(value = RabbitMQConstant.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC, durable="true", ignoreDeclarationExceptions = "true"), key = RabbitMQConstant.TOPIC_ROUTING_KEY_JING))
	public void receiveTopicMessage(@Payload DataDto dto, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
		logger.info("Topic Exchange 收到queue[{}]消息: {}：========================> ", queue, dto);
	}

	@DanteTopicListener
	public void receiveTopicCustomerAnnonationMessage(DataDto dto) {
		logger.info("Topic Exchange 使用自定义注解收到消息：========================> " + dto);
	}
	
	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(durable="false"), exchange = @Exchange(value = RabbitMQConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT, durable="false", ignoreDeclarationExceptions = "true")))
	public void receiveFanoutMessage1(DataDto dto) {
		logger.info("Fanout Exchange收听者【1】收到消息：========================> " + dto);
	}
	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(durable="false"), exchange = @Exchange(value = RabbitMQConstant.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT, durable="false", ignoreDeclarationExceptions = "true")))
	public void receiveFanoutMessage2(DataDto dto) {
		logger.info("Fanout Exchange收听者【2】收到消息：========================> " + dto);
	}

}
