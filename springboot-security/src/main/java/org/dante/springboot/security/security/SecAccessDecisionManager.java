package org.dante.springboot.security.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecAccessDecisionManager extends AbstractAccessDecisionManager {

	public SecAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}

	/**
	 * authentication: 认证实体
	 * object: 
	 * configAttributes: 
	 */
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (null == configAttributes) {  
            return;  
        } 
		Iterator<ConfigAttribute> cons = configAttributes.iterator();  
        while(cons.hasNext()){  
        	if (authentication == null) {
                throw new AccessDeniedException("当前访问没有权限");
            }
            ConfigAttribute ca = cons.next();  
            String needPermission = ((SecurityConfig) ca).getAttribute();  
            //authority 为用户所被赋予的权限，needPermission为访问相应的资源应具有的权限  
            for (GrantedAuthority authority : authentication.getAuthorities()) {  
                if ((SecurityConstant.ROLE_PREFIX + needPermission.trim()).equals(authority.getAuthority().trim())) {  
                    return;  
                }  
            }  
        }  
        throw new AccessDeniedException("当前访问没有权限");
	}
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return super.supports(attribute);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return super.supports(clazz);
	}
	
	
}
