package org.dante.springboot.springbootjwtserver.controller;

import java.util.List;

import org.dante.springboot.springbootjwtserver.dao.RoleDAO;
import org.dante.springboot.springbootjwtserver.po.RolePO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleDAO roleDAO;
	
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	@GetMapping("/query")
	public List<UserPO> queryUsers() throws Exception {
		return userService.findAll();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/query_role")
	public List<RolePO> queryUserRole() {
		return roleDAO.findAll();
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/query/{id}")
	public UserPO queryUserById(@PathVariable Long id) throws Exception {
		return userService.findOne(id);
	}
	
}
