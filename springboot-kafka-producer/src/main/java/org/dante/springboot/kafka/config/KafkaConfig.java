package org.dante.springboot.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic initialTopic() {
        return new NewTopic("topic.spirit.msg", 3,  (short) 1);
    }
	
}
