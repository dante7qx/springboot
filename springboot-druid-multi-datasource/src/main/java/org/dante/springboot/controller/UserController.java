package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.bo.shiro.UserBO;
import org.dante.springboot.dao.shiro.UserDAO;
import org.dante.springboot.mapper.shiro.UserMapper;
import org.dante.springboot.po.shiro.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/users/jpa")
	public List<UserPO> findByJpa() {
		return userDAO.findAll();
	}
	
	@GetMapping("/users/mybatis")
	public List<UserBO> findByMatis() {
		return userMapper.queryUsers();
	}
	
}
