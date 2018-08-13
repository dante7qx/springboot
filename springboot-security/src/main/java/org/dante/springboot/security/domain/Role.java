package org.dante.springboot.security.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Column(name = "role_desc")
	private String roleDesc;

	@ManyToMany(cascade = { CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinTable(name = "t_role_authority", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })
	private Set<Authority> authoritys;

	
	public Role() {
	}
	
	public Role(Long id) {
		this.id = id;
	}
	
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

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<Authority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Set<Authority> authoritys) {
		this.authoritys = authoritys;
	}


}
