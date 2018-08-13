package org.dante.springboot.security.security;

import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userService.findByLoginAccount(username);
		} catch (Exception e) {
			logger.error("loadUserByUsername error." + username, e);
			throw new UsernameNotFoundException("用户名"+username+"认证失败！");
		}
		if(user == null) {
			throw new UsernameNotFoundException("用户名"+username+"不存在！");
		}
		return new SecAuthUser(user);
	}
}
