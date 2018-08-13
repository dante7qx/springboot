package org.dante.springboot.security.dto.req;

import java.util.Set;

public class UserReqDto {

	private Long id;
	private String account;
	private String name;
	private String password;
	private String email;
	private Boolean ldapUser;
	private Set<Long> roleIds;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(Boolean ldapUser) {
		this.ldapUser = ldapUser;
	}

	public Set<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoles(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}

	@Override
	public String toString() {
		return "UserReqDto [id=" + id + ", account=" + account + ", name=" + name + ", password=" + password
				+ ", email=" + email + ", roleIds=" + roleIds + "]";
	}

}
