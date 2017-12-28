package org.dante.springboot.rabbitmq.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spirit.rabbitmq")
public class SpiritRabbitProperties {
	
	private String defaultQueue = "sbr.default.queue";
	private String ackQueue = "sbr.ack.queue";
	private String topicQueue = "sbr.topic.queue";

	private String directExchange;
	private String fanoutExchange;
	private String topicExchange;
	
	private String directRoutingKey;
	private String topicRoutingKey;
	
}
