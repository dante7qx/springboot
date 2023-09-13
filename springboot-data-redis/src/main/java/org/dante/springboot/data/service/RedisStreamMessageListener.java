package org.dante.springboot.data.service;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听消息
 * 
 * @author dante
 *
 */
@Slf4j
public class RedisStreamMessageListener implements StreamListener<String, MapRecord<String, String, String>> {

	private final RedisStreamService redisStreamService;

	public RedisStreamMessageListener(RedisStreamService redisStreamService) {
		this.redisStreamService = redisStreamService;
	}

	@Override
	public void onMessage(MapRecord<String, String, String> entries) {
		try {
			// check用于验证key和对应消息是否一直
			log.info("stream name :{}, body:{}, check:{}", entries.getStream(), entries.getValue(), (entries.getStream().equals(entries.getValue().get("name"))));
			redisStreamService.delField(entries.getStream(), entries.getId().getValue());
		} catch (Exception e) {
			log.error("error message:{}", e.getMessage());
		}

	}

}
