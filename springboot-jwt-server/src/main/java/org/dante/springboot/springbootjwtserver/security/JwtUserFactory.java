package org.dante.springboot.springbootjwtserver.security;

import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dante.springboot.springbootjwtserver.po.RolePO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.hibernate.annotations.NotFound;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUserFactory {
	public static JwtUserDetails create(UserPO user) {
		return new JwtUserDetails(user.getId(), user.getUserName(), user.getPassword(), user.getEmail(),
				user.getLastPasswordResetDate(),
				buildGrantedAuthoritys(user.getRoles()));
	}

	private static Set<GrantedAuthority> buildGrantedAuthoritys(Set<RolePO> roles) {
		Set<GrantedAuthority> authoritys = new HashSet<>();
		if(!CollectionUtils.isEmpty(roles)) {
			for (RolePO role : roles) {
				authoritys.add(new SimpleGrantedAuthority(role.getCode()));
			}
		}
		return authoritys;
	}
}
