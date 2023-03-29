package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.po.UserPO;
import org.dante.springboot.service.OrderService;
import org.dante.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdbcController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/users")
	public List<UserPO> queryUsers() {
		 return userService.queryUsers();
	}
	
	@GetMapping("/add_order")
	public int addorder() {
		 return orderService.insertOrder();
	}
}
