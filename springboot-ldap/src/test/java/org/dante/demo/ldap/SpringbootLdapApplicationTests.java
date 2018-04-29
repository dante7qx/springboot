package org.dante.demo.ldap;

import java.io.IOException;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
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

	@Test
	public void testLdapConnection() {
		LdapConnection connection = new LdapNetworkConnection("10.72.8.10", 389);
		try {
			connection.bind("cn=ch.sun,dc=HNA,dc=NET", "121121");
			System.out.println(connection.isConnected());
		} catch (LdapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
