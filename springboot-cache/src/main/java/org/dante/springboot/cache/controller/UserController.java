package org.dante.springboot.cache.controller;

import java.math.BigDecimal;
import java.util.List;

import org.dante.springboot.cache.po.UserPO;
import org.dante.springboot.cache.service.UserService;
import org.dante.springboot.cache.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/all")
	public List<UserPO> findUsers() {
		return userService.findUsers();
	}
	
	@GetMapping("/user/{id}")
	public UserVO findById(@PathVariable Long id) {
		return userService.findUser(id);
	}
	
	@GetMapping("/user_account/{account}")
	public UserPO findByAccount(@PathVariable String account) {
		return userService.findByAccount(account);
	}
	
	@GetMapping("/user/add")
	public UserVO addUser() {
		UserVO vo = new UserVO("帐号"+Math.random(), "名称"+Math.random(), 32,  BigDecimal.valueOf(87.62));
		return userService.insert(vo);
	}
	
	@GetMapping("/user/update/{id}")
	public UserVO updateUser(@PathVariable Long id) {
		UserVO userVO = userService.findUser(id);
		userVO.setAccount("更新帐号"+Math.random());
		userVO.setName("更新名称"+Math.random());
		return userService.update(userVO);
	}
	
	@GetMapping("/user/del/{id}")
	public void deleteById(@PathVariable Long id) {
		userService.delete(id);
	}
	
}	
