package org.dante.springboot.springbootjwtserver.service;

import java.util.List;

import org.dante.springboot.springbootjwtserver.po.UserPO;

public interface UserService {
	UserPO findByUserName(String userName) throws Exception;
	
	List<UserPO> findAll() throws Exception;
	
	UserPO findOne(Long id) throws Exception;
}
