package org.dante.springboot.security.security;

import java.util.Collection;
import java.util.Set;

import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Sets;

public class SecAuthUser implements UserDetails {
	private static final long serialVersionUID = -4883090636238491688L;
	
	private User user;
	
	public SecAuthUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auths = Sets.newHashSet();
		Set<Role> roles = this.user.getRoles();
		if(roles != null) {
			Set<Authority> authoritys = null;
			for (Role role : roles) {
				authoritys = role.getAuthoritys();
				if(authoritys != null) {
					for (Authority authority : authoritys) {
						auths.add(new SimpleGrantedAuthority(authority.getCode()));
					}
				}
			}
		}
		return auths;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getAccount();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUser() {
		return user;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SecAuthUser) {
			return getUsername().equals(((SecAuthUser) obj).getUsername());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return getUsername().hashCode(); 
	}
}
