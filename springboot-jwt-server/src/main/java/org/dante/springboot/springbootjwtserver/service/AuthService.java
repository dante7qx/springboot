package org.dante.springboot.springbootjwtserver.service;

import org.dante.springboot.springbootjwtserver.po.UserPO;

public interface AuthService {
	
	/**
	 * 用户注册
	 */
	UserPO register(UserPO user);
	
	/**
	 * 用户登录，成功后返回 Token
	 */
	String login(String username, String password);
	
	/**
	 * 刷新token，用于取得新的token
	 */
	String refresh(String oldToken);
}
