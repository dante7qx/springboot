package org.dante.demo.amqp.config;

import java.io.UnsupportedEncodingException;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.dante.demo.amqp.hello.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用容器处理消息
 * 
 * @author dante
 *
 */
@Configuration 
public class ContainerConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(Receiver.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMQConstant.CONTAINER_QUEUE);
        container.setMessageListener(exampleListener());
        return container;
    }

	@Bean
    public MessageListener exampleListener() {
        return new MessageListener() {
            public void onMessage(Message message) {
            	try {
					String msg = new String(message.getBody(), "UTF-8");
					logger.info("使用Container收到消息: " + msg);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
        };
    }
	
}
