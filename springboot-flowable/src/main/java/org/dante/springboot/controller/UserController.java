package org.dante.springboot.controller;

import org.dante.springboot.util.UserUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	/**
	 * 模拟登陆
	 * 
	 * @return
	 */
	@GetMapping("/login/{userId}")
	public String login(@PathVariable String userId) {
		UserUtil.setCurrentUser(userId);
		return userId;
	}
	
	
	
}
