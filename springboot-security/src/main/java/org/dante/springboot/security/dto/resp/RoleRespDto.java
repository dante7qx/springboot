package org.dante.springboot.security.dto.resp;

import java.util.Set;

import com.google.common.collect.Sets;

public class RoleRespDto {
	private Long id;
	private String name;
	private String roleDesc;
	private Set<Long> authorityIds;

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

	public Set<Long> getAuthorityIds() {
		if(authorityIds == null) {
			authorityIds = Sets.newHashSet();
		}
		return authorityIds;
	}

	public void setAuthorityIds(Set<Long> authorityIds) {
		this.authorityIds = authorityIds;
	}
}
