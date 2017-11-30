package org.dante.springboot.mapper.shiro;

import java.util.List;

import org.dante.springboot.bo.shiro.UserBO;

public interface UserMapper {
	
	public List<UserBO> queryUsers();
	
	public void insertUser(UserBO user);
	
}
