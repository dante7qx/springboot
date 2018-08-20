package org.dante.demo.ldap.config;

import org.dante.demo.ldap.prop.LdapProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.StringUtils;

@Configuration
public class LdapConfig {
	@Autowired
	private LdapProp ldapProp;
	
	@Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSourceTarget());
    }

	@Bean
	public LdapContextSource contextSourceTarget() {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl(ldapProp.getUrl());
		ldapContextSource.setUserDn(ldapProp.getUserDn());
		if(!StringUtils.isEmpty(ldapProp.getBase())) {
			ldapContextSource.setBase(ldapProp.getBase());
		}
		if(!StringUtils.isEmpty(ldapProp.getPassword())) {
			ldapContextSource.setPassword(ldapProp.getPassword());
		}
		
		return ldapContextSource;
	}
}
