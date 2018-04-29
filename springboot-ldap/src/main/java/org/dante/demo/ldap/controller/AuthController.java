package org.dante.demo.ldap.controller;

import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.dante.demo.ldap.service.LdapAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private LdapAuthenticationService ldapAuthenticationService;

	@GetMapping("/auth/{u}/{p}")
	public String authOS(@PathVariable("u") String u, @PathVariable("p") String p) {
		logger.info("user: {}, pwd: {}", u, p);
		ldapAuthenticationService.authenticateOpenShift(u, p);
		return "ok";
	}
	
	@GetMapping("/auth2/{u}/{p}")
	public String auth2OS(@PathVariable("u") String u, @PathVariable("p") String p) {
		logger.info("user: {}, pwd: {}", u, p);
//		LdapConnection connection = new LdapNetworkConnection("10.48.2.17", 389);
//		LdapConnection connection = new LdapNetworkConnection("127.0.0.1", 389);
		LdapConnection connection = new LdapNetworkConnection("10.70.94.75", 389);
		try {
			connection.bind(u, p);
			logger.info("连接状态 {}, 认证状态 {}.", connection.isConnected(), connection.isAuthenticated());
			/*
			Entry entry = new DefaultEntry(
				     "cn=xxs,dc=mycorp,dc=com",
				     "objectClass: person",
				     "cn: xxs", 
				     "sn: xxs", 
				     "userPassword: 123456");
				   connection.add(entry);
		   */
		} catch (LdapException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.unBind();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "ok";
	}
	
}
