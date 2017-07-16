package org.dante.springboot.springbootjwtserver.service;

import org.dante.springboot.springbootjwtserver.po.UserPO;

public interface AuthService {
	
	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	public UserPO register(UserPO user);
	
	/**
	 * 用户登录，成功后返回 Token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password);
	
	/**
	 * 刷新token，用于取得新的token
	 * 
	 * @param oldToken
	 * @return
	 */
	public String refresh(String oldToken);
}
