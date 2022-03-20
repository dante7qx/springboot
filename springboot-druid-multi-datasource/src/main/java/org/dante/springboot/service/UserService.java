package org.dante.springboot.service;

import org.dante.springboot.bo.springboot2.UserBO;
import org.dante.springboot.dao.springboot2.UserDAO;
import org.dante.springboot.mapper.springboot2.UserMapper;
import org.dante.springboot.po.springboot2.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public void insertWithJpa() {
		UserPO user = new UserPO();
		user.setUserName("[jpa] - 测试");
		user.setPassword("TestPwd");
		userDAO.save(user);
		
		UserPO user1 = new UserPO();
		user1.setUserName("[jpa] - 测试");
		user1.setPassword("TestPwd1");
		userDAO.save(user1);
	}
	
	
	@Transactional
	public void insertWithMybatis() {
		UserBO user = new UserBO();
		user.setUserName("[mybatis] - 测试");
		user.setPassword("TestPwd");
		userMapper.insertUser(user);
		
		UserBO user1 = new UserBO();
		user1.setUserName("[mybatis] - 测试");
		user1.setPassword("TestPwd1");
		userMapper.insertUser(user1);
	}
}
