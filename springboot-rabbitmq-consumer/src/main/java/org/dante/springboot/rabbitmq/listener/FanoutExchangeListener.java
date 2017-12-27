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
public class FanoutExchangeListener {

	@RabbitListener(bindings=@QueueBinding(value=@Queue(autoDelete="true") ,exchange=@Exchange(value="${spirit.rabbitmq.fanout-exchange}",durable="true",type=ExchangeTypes.FANOUT)))
	public void receive1(Object msg) {
		log.info("听众1收到消息 {}.", msg);
	}
	
	@RabbitListener(bindings=@QueueBinding(value=@Queue(autoDelete="true") ,exchange=@Exchange(value="${spirit.rabbitmq.fanout-exchange}",durable="true",type=ExchangeTypes.FANOUT)))
	public void receive2(PubMsg msg) {
		log.info("听众2收到消息 {}.", msg);
	}
	
	@RabbitListener(bindings=@QueueBinding(value=@Queue(value="fanout.receive3", autoDelete="true") ,exchange=@Exchange(value="${spirit.rabbitmq.fanout-exchange}",durable="true",type=ExchangeTypes.FANOUT)))
	public void receive3(PubMsg msg) {
		log.info("听众3收到消息 {}.", msg);
	}
}
