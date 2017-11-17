package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.shiro.UserDAO;
import org.dante.springboot.po.shiro.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDAO;

	@GetMapping("/users")
	public List<UserPO> findAll() {
		return userDAO.findAll();
	}
	
}
