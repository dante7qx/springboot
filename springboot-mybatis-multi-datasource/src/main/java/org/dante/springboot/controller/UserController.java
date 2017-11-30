package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.bo.shiro.UserBO;
import org.dante.springboot.mapper.shiro.UserMapper;
import org.dante.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<UserBO> findByMatis() {
		return userMapper.queryUsers();
	}
	
	@GetMapping("/users/save")
	public List<UserBO> insertByMabatis() {
		userService.insertWithMybatis();
		return userMapper.queryUsers();
	}
	
}
