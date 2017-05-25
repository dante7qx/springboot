package org.dante.demo.ldap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {
	@Value("${ldap.url}")
	private String ldapUrl;
	
	@Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSourceTarget());
    }

	@Bean
	public LdapContextSource contextSourceTarget() {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(ldapUrl);
		ldapContextSource.setUserDn("dc=HNA,dc=NET");
		return ldapContextSource;
	}
}
