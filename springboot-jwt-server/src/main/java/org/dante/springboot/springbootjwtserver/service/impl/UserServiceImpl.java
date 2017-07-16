package org.dante.springboot.springbootjwtserver.service.impl;

import java.util.List;

import org.dante.springboot.springbootjwtserver.dao.UserDAO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserPO findByUserName(String userName) throws Exception {
		return userDAO.findByUserName(userName);
	}

	@Override
	public List<UserPO> findAll() throws Exception {
		return userDAO.findAll();
	}

	@Override
	public UserPO findOne(Long id) throws Exception {
		return userDAO.findOne(id);
	}

}
