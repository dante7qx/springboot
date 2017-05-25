package org.dante.demo.amqp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dante.demo.amqp.constant.RabbitMQConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener(bindings = @QueueBinding(value = @Queue(durable="true"), exchange = @Exchange(value = RabbitMQConstant.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC, durable="true", ignoreDeclarationExceptions = "true"), key = RabbitMQConstant.TOPIC_ROUTING_KEY_STAR))
public @interface DanteTopicListener {

}
