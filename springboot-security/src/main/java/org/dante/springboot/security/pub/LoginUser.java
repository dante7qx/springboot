package org.dante.springboot.security.pub;

import java.io.Serializable;
import java.util.Set;

import org.dante.springboot.security.domain.Role;

public class LoginUser implements Serializable {
	private static final long serialVersionUID = -517180913345171081L;
	
	private Long id;
	private String account;
	private String name;
	private String email;
	private Set<Role> roles;
	
	public LoginUser() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LoginUser) {
			return this.getAccount().equals(((LoginUser) obj).getAccount());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return account.hashCode();
	}

	@Override
	public String toString() {
		return "LoginUser [account=" + account + ", name=" + name + ", email=" + email + "]";
	}
	
}
