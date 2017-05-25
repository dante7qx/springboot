package org.dante.demo.ldap;

import org.dante.demo.ldap.service.LdapAuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootLdapApplicationTests {
	
	@Autowired
	private LdapAuthenticationService ldapAuthenticationService;
	
	@Test
	public void authenticate() {
		String userName = "ch.sun";
		String password = "qye.832";
		ldapAuthenticationService.authenticate(userName, password);
	}

}
