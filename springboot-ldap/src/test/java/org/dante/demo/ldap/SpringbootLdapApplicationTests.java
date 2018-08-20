package org.dante.demo.ldap;

import org.dante.demo.ldap.service.LdapAuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 参考：https://blog.csdn.net/huanxue517/article/details/51881150
 * 
 * @author dante
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootLdapApplicationTests {
	
	@Autowired
	private LdapAuthenticationService ldapAuthenticationService;
	
	@Test
	public void authenticate() {
		String userName = "spiritdev";
		String password = "QK0VYV";
		ldapAuthenticationService.authenticate(userName, password);
	}
	
	@Test
	public void authenticateHNA() {
		String userName = "ch.sun";
		String password = "********";
		ldapAuthenticationService.authenticateHNA(userName, password);
	}

}
