package org.dante.demo.amqp.config;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

/**
 * 使用自定义@RabbitListener annotations时(任何自定义配置)， 需要在配置类上添加 @EnableRabbit
 * 
 * @author dante
 */
@Configuration
@EnableRabbit
public class RabbitMqConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

	@Autowired
	private ConnectionFactory connectionFactory;

	/**
	 * 1、声明CachingConnectionFactory
	 * 
	 * 2、在application.yml中定义，然后
	 * 
	 * @Autowired ConnectionFactory connectionFactory;
	 * 
	 * @return
	 */
		/*
	 	@Bean public ConnectionFactory connectionFactory() {
		  CachingConnectionFactory connectionFactory = new
		  CachingConnectionFactory("localhost");
		  connectionFactory.setUsername("dante");
		  connectionFactory.setPassword("123456"); return connectionFactory; 
		}
		
		*/
	
	@Bean
	public RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		// factory.setPrefetchCount(5);
		// factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return factory;
	}

	@Bean
	public Queue helloQueue() {
		return new Queue(RabbitMQConstant.HELLO_QUEUE, true);
	}

	@Bean
	public Queue containerQueue() {
		return new Queue(RabbitMQConstant.CONTAINER_QUEUE, true);
	}

	/**
	 * queue不需要已经存在，并绑定到一个交换器 使用Exchange绑定Queue时，Queue会自动声明
	 * 
	 * @return
	 */
	@Bean
	public Queue directQueue() {
//		return new Queue(RabbitMQConstant.DIRECT_QUEUE, true);
		return QueueBuilder.durable(RabbitMQConstant.DIRECT_QUEUE)
//				.autoDelete()
//				.exclusive()
				.build();
	}

	/**
	 * 根据routingKey分发到指定的队列
	 * 
	 * @return
	 */
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(RabbitMQConstant.DIRECT_EXCHANGE, true, false);
	}

	/**
	 * 根据routingKey(多关键字匹配)分发到指定的队列
	 * 
	 * @return
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(RabbitMQConstant.TOPIC_EXCHANGE, true, false);
	}

	/**
	 * 消息广播，无routingKey的概念，消息会分发到所有绑定fanout交换器的队列上
	 * 
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(RabbitMQConstant.FANOUT_EXCHANGE, false, false);
	}

	/**
	 * 匿名Queue
	 * @return
	 */
	@Bean
	public Queue annoQueue1() {
		return new AnonymousQueue(new AnonymousQueue.Base64UrlNamingStrategy());
	}
	
	@Bean
	public Queue annoQueue2() {
		return new AnonymousQueue(new AnonymousQueue.Base64UrlNamingStrategy("custom.dante-gen-"));
	}
	
	/**
	 * 应答模式测试交换器
	 * 
	 * @return
	 */
	@Bean
	public DirectExchange confirmExchange() {
		return new DirectExchange(RabbitMQConstant.CONFIRM_EXCHANGE);
	}

	@Bean
	public Queue confirmQueue() {
		return new Queue(RabbitMQConstant.CONFIRM_QUEUE, true);
	}

	@Bean  
    public Binding confirmBinding() {  
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with(RabbitMQConstant.CONFIRM_ROUTING_KEY);  
    }
	
	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(confirmQueue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				byte[] body = message.getBody();
				MessageProperties msgProp = message.getMessageProperties();
				long deliveryTag = msgProp.getDeliveryTag();
				logger.info("Confirm模式收到消息: {}, DeliveryTag: {} ", new String(body), deliveryTag);
				channel.basicAck(deliveryTag, false); // 确认消息成功消费
			//	BasicProperties replyProps = new BasicProperties.Builder().correlationId(msgProp.getCorrelationIdString()).build();
			//	channel.basicPublish("", msgProp.getReplyTo(), true, replyProps, body);
			}
		});
		return container;
	}
}
