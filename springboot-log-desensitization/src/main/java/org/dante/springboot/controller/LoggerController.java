package org.dante.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoggerController {
	private static ObjectMapper MAPPER = new ObjectMapper();
	
	@GetMapping("/")
	public String logMasking() throws JsonProcessingException {
		Map<String, String> user = new HashMap<>();
	    user.put("user_id", "123456");
	    user.put("mobile", "18888888888");
	    user.put("address", "朝阳区百子湾街道某小区1单元101");
	    user.put("city", "北京市");
	    user.put("country", "中国");
	    user.put("email", "heiz123@163.com");
	    String userJson = MAPPER.writeValueAsString(user);
	    log.info("User info: {}", userJson);
	    return userJson;
	}
	
}
