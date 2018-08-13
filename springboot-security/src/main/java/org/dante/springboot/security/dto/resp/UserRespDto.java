package org.dante.springboot.security.dto.resp;

import java.util.Set;

import com.google.common.collect.Sets;

public class UserRespDto {
	private Long id;
	private String account;
	private String name;
	private String email;
	private Boolean ldapUser;
	private Set<Long> roleIds;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(Boolean ldapUser) {
		this.ldapUser = ldapUser;
	}

	public Set<Long> getRoleIds() {
		if(roleIds == null) {
			roleIds = Sets.newHashSet();
		}
		return roleIds;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
