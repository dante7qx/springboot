package org.dante.springboot;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.dante.springboot.kafka.vo.PubMsg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringbootKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}
	
	
	/**
     * 监听Topic主题,有消息就读取
     * @param message
     */
	
    @KafkaListener(topics = {"SPIRIT_P_M"})
    public void receiveMessage(ConsumerRecord<?, ?> record){
    	Optional<?> message = Optional.ofNullable(record.value());
    	if (message.isPresent()) {
    		PubMsg pubMsg = (PubMsg) message.get();
    		//收到通道的消息之后执行业务操作
            log.info("收到Topic [SPIRIT_P_M] 的消息 -> {}", pubMsg);
    	}
        
    }
}
