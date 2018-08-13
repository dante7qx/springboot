package org.dante.springboot.security.service;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthenticationService {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	
	public boolean authenticate(String userName, String password) {
		DirContext dirContext = null; 
		try {
			dirContext = ldapTemplate.getContextSource().getContext(userName+"@HNA.NET", password);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			LdapUtils.closeContext(dirContext);
		}
	}
	
	
	
	
}
