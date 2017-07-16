package org.dante.springboot.springbootjwtserver.service;

import java.util.List;

import org.dante.springboot.springbootjwtserver.po.UserPO;

public interface UserService {
	public UserPO findByUserName(String userName) throws Exception;
	
	public List<UserPO> findAll() throws Exception;
	
	public UserPO findOne(Long id) throws Exception;
}
