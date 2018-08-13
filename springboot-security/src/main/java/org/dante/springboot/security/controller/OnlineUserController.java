package org.dante.springboot.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/online")
public class OnlineUserController {

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@RequestMapping(value = "/query", produces = "application/json")
	public List<Object> queryOnlineUser() {
		List<Object> principals = null;
		try {
			principals = sessionRegistry.getAllPrincipals();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return principals;
	}
}
