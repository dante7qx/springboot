package org.dante.springboot.security.util;

import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.pub.LoginUser;
import org.dante.springboot.security.security.SecAuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserUtil {

	public static LoginUser loginUser() {
		LoginUser loginUser = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof SecAuthUser) {
			loginUser = convertLoginUser(((SecAuthUser) principal).getUser());
		}
		return loginUser;
	}

	private static LoginUser convertLoginUser(User user) {
		LoginUser loginUser = new LoginUser();
		loginUser.setId(user.getId());
		loginUser.setName(user.getName());
		loginUser.setAccount(user.getAccount());
		loginUser.setEmail(user.getEmail());
		loginUser.setRoles(user.getRoles());
		return loginUser;
	}
}
