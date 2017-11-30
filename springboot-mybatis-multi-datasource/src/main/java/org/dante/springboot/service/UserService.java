package org.dante.springboot.service;

import org.dante.springboot.bo.shiro.UserBO;
import org.dante.springboot.mapper.shiro.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public void insertWithMybatis() {
		UserBO user = new UserBO();
		user.setUserName("测试");
		user.setPassword("TestPwd");
		userMapper.insertUser(user);
		
		UserBO user1 = new UserBO();
		user1.setUserName("测试");
		user1.setPassword("TestPwd1");
		userMapper.insertUser(user1);
	}
}
