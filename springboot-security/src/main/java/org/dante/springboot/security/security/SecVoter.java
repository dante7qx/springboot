package org.dante.springboot.security.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecVoter extends RoleVoter {

	@Override
	public String getRolePrefix() {
		return super.getRolePrefix();
	}

	@Override
	public void setRolePrefix(String rolePrefix) {
		super.setRolePrefix(rolePrefix);
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return super.supports(attribute);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return super.supports(clazz);
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		if(authentication == null) {
			return ACCESS_DENIED;
		}
		int result = ACCESS_ABSTAIN;
		Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

		for (ConfigAttribute attribute : attributes) {
			if (this.supports(attribute)) {
				result = ACCESS_DENIED;

				// Attempt to find a matching granted authority
				for (GrantedAuthority authority : authorities) {
					if (attribute.getAttribute().equals(SecurityConstant.ROLE_PREFIX + authority.getAuthority())) {
						return ACCESS_GRANTED;
					}
				}
			}
		}

		return result;
	}
	
	Collection<? extends GrantedAuthority> extractAuthorities(
			Authentication authentication) {
		return authentication.getAuthorities();
	}

}
