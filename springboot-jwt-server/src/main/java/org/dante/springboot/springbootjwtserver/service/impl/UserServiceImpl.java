package org.dante.springboot.springbootjwtserver.service.impl;

import org.dante.springboot.springbootjwtserver.dao.UserDAO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
		return userDAO.getOne(id);
	}

}
