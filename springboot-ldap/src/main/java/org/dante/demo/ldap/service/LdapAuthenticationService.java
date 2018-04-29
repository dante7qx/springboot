package org.dante.demo.ldap.service;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;

import org.dante.demo.ldap.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthenticationService {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	
	public void authenticate(String userName, String password) {
		DirContext dirContext = null; 
		try {
			dirContext = ldapTemplate.getContextSource().getContext(userName+"@HNA.NET", password);
			
			NamingEnumeration<NameClassPair> list = dirContext.list("ou=海航集团,dc=HNA,dc=NET");
			while(list.hasMore()) {
				System.out.println(list.next());
			}
			
			System.out.println(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LdapUtils.closeContext(dirContext);
		}
	}
	
	
	public void authenticateOpenShift(String userName, String password) {
		DirContext dirContext = null; 
		try {
			dirContext = ldapTemplate.getContextSource().getContext(userName, password);
			System.out.println("111111111111111111111");
			
			/*
			Person p = new Person();
			p.setCn("sxx");
			p.setSn("sxx");
			p.setUserPassword("123456");
			ldapTemplate.create(p);
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LdapUtils.closeContext(dirContext);
		}
	}
	
}
