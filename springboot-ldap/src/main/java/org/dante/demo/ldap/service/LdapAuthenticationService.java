package org.dante.demo.ldap.service;

import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LdapAuthenticationService {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	/**
	 * 普通 LDAP 认证
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean authenticate(String userName, String password) {
		boolean authenticate = ldapTemplate.authenticate("", "(cn="+userName+")", password);
		log.info("===> {} LDAP 认证 {}", userName, authenticate);
		return authenticate;
	}
	
	/**
	 * 海航集团域认证
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean authenticateHNA(String userName, String password) {
		boolean authenticate = false;
		DirContext dirContext = null; 
		try {
			userName = userName.concat("@HNA.NET");
			dirContext = ldapTemplate.getContextSource().getContext(userName, password);
			authenticate = true;
		} catch (Exception e) {
			log.error("LDAP authenticate error.", e);
		} finally {
			LdapUtils.closeContext(dirContext);
		}
		log.info("===> {} LDAP 认证 {}", userName, authenticate);
		return authenticate;
	}
	
}
