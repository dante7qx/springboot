package org.dante.springboot.data.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.data.service.RedisStreamMessageListener;
import org.dante.springboot.data.service.RedisStreamService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.data.redis.stream.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RedisStreamConfig {
	
	private final RedisStreamService redisStreamService;              // Redis Stream工具类
	private final RedisStreamMessageListener redisStreamMessageListener;   // 监听类
    
	@Value("${redis-stream.keys}")
	private String[] redisStreamKeys; // redis stream Key 数组
	@Value("${redis-stream.groups}")
	private String[] groups; // redis stream 群组数组
	
    
    /**
     * 注入工具类和监听类
     */
    public RedisStreamConfig(RedisStreamService redisStreamService){
        this.redisStreamService = redisStreamService;
        this.redisStreamMessageListener = new RedisStreamMessageListener(redisStreamService);
    }
    
    @Bean
    public List<Subscription> subscription(RedisConnectionFactory factory){
        List<Subscription> resultList = new ArrayList<>();
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .build();
        for (String redisStreamKey : redisStreamKeys) {
            initStream(redisStreamKey,groups[0]);
            StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(factory,options);
            Subscription subscription = listenerContainer.receiveAutoAck(Consumer.from(groups[0], this.getClass().getName()),
                    StreamOffset.create(redisStreamKey, ReadOffset.lastConsumed()), redisStreamMessageListener);
            resultList.add(subscription);
            listenerContainer.start();
        }
        return resultList;
    }
    
    private void initStream(String key, String group){
        boolean hasKey = redisStreamService.hasKey(key);
        if(!hasKey){
            Map<String,Object> map = new HashMap<>();
            map.put("field","value");
            RecordId recordId = redisStreamService.addStream(key, map);
            redisStreamService.addGroup(key,group);
            // 将初始化的值删除掉
            redisStreamService.delField(key,recordId.getValue());
            log.info("stream:{}-group:{} initialize success",key,group);
        }
    }

}
