package org.dante.springboot.rabbitmq.config;

import org.dante.springboot.rabbitmq.prop.SpiritRabbitProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Exchange、Queue在做绑定时，所有的配置必须一致 (durable、auto-delete、exclusive)
 * 
 * @author dante
 *
 */
@EnableRabbit
@Configuration
public class RabbitMQConfig {

	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private SpiritRabbitProperties spiritRabbitProperties;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}
	
	/**
	 * 默认使用 direct 交换器，使用 Queue 的名字做为 Routingkey
	 * 
	 * @return
	 */
	@Bean
	public Queue defaultQueue() {
		return QueueBuilder.nonDurable(spiritRabbitProperties.getDefaultQueue()).build();
	}

	/****************************************************************************/
	// direct exchange，按照routingkey分发到指定队列，用于point - point
	@Bean
	public DirectExchange directExchange() {
		return (DirectExchange) ExchangeBuilder.directExchange(spiritRabbitProperties.getDirectExchange()).durable(true).build();
	}

	@Bean
	public Queue directQueue() {
		// 匿名 Queue
		return QueueBuilder.nonDurable().autoDelete().build();
	}

	@Bean
	public Binding directBinding() {
		return BindingBuilder.bind(directQueue()).to(directExchange()).with(spiritRabbitProperties.getDirectRoutingKey());
	}
	/****************************************************************************/
	
	/****************************************************************************/
	// fanout exchange, 广播消息，无routingKey的概念，消息会分发到所有绑定fanout交换器的队列上
	@Bean
	public FanoutExchange fanoutExchange() {
		return (FanoutExchange) ExchangeBuilder.fanoutExchange(spiritRabbitProperties.getFanoutExchange()).durable(true).build();
	}
	/****************************************************************************/
	
	/****************************************************************************/
	// topic exchange, 发布/订阅
	@Bean
	public TopicExchange topicExchange() {
		return (TopicExchange) ExchangeBuilder.topicExchange(spiritRabbitProperties.getTopicExchange()).durable(true).build();
	}
	
}
