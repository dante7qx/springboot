package org.dante.demo.ldap.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "ldap")
public class LdapProp {
	private String base;
	private String url;
	private String userDn;
	private String password;
}
