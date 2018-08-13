package org.dante.springboot.security.util;

import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.pub.LoginUser;

/**
 * 使用ThreadLocal存储当前用户，在Filter中先获取session，然后在存储当前用户
 * 
 * 优化：使用高效的线程池进行线程的管理
 * 
 * @author dante
 *
 */
@Deprecated
public class CurrentUserUtil {
	private final static ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();
	
	public final static LoginUser getCurrentUser() {
		return userHolder.get();
	}
	
	public final static void setCurrentUser(User user) {
		userHolder.set(convertLoginUser(user));
	}
	
	public final static void remove() {
		userHolder.remove();
	}
	
	private static LoginUser convertLoginUser(User user) {
		LoginUser loginUser = new LoginUser();
		loginUser.setName(user.getName());
		loginUser.setAccount(user.getAccount());
		loginUser.setEmail(user.getEmail());
		loginUser.setRoles(user.getRoles());
		return loginUser;
	}
}
