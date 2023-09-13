package org.dante.springboot.data.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.data.config.RedisStreamConfig;
import org.dante.springboot.data.service.RedisStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
public class RedisStreamController {

	@Value("${redis-stream.keys}")
	private String[] redisStreamKeys;

	@Autowired
	RedisConnectionFactory factory;

	@Autowired
	RedisStreamConfig redisStreamConfig;

	private final RedisStreamService redisStreamService;

	public RedisStreamController(RedisStreamService redisStreamService) {
		this.redisStreamService = redisStreamService;
	}

	/**
	 * 显示手动监听演示
	 */
	@GetMapping("/listener")
	public String listener() {
		redisStreamConfig.subscription(factory);
		return "监听成功";
	}

	@GetMapping("/sendTest/{streamName}")
	public String addStream(@PathVariable String streamName) {
		Map<String, Object> message = new HashMap<>();
		message.put("test", "hello redismq");
		message.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return redisStreamService.addStream(streamName, message).getValue();
	}

	@GetMapping("/getStream")
	public List<MapRecord<String, Object, Object>> getStream(String key) {
		return redisStreamService.getAllStream(key);
	}

	@GetMapping("/groupRead")
	public void getStreamByGroup(String key, String groupName, String consumerName) {
		redisStreamService.getStreamByGroup(key, groupName, consumerName);
	}

	@GetMapping("/moreTest/{count}")
	public void moreAddTest(@PathVariable("count") Integer count) {
		for (int i = 0; i < count; i++) {
			Map<String, Object> message1 = new HashMap<>();
			message1.put("name", "mystream1");
			message1.put("msgName", "消息-" + i);
			message1.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			Map<String, Object> message2 = new HashMap<>();
			message2.put("name", "mystream2");
			message2.put("recordName", "记录-" + i);
			message2.put("send time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			redisStreamService.addStream(redisStreamKeys[0], message1);
			redisStreamService.addStream(redisStreamKeys[1], message2);
		}
	}
}
