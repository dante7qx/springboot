package org.dante.springboot.springbootjwtserver.security;

import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class JwtUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		try {
			UserPO user = userService.findByUserName(userName);
			if (user == null) {
	            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
	        } else {
	            return JwtUserFactory.create(user);
	        }
		} catch (Exception e) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName, e));
		}
		
	}

}
