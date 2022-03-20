package org.dante.springboot.mapper.springboot2;

import java.util.List;

import org.dante.springboot.bo.springboot2.UserBO;

public interface UserMapper {
	
	public List<UserBO> queryUsers();
	
	public void insertUser(UserBO user);
	
}
