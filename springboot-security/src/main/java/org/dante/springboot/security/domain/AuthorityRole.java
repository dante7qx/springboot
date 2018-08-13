package org.dante.springboot.security.domain;

import java.io.Serializable;

public class AuthorityRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long pid;
	private String name;
	private String code;
	private String authorityDesc;
	private int showOrder;
	private Long roleId;
	private boolean hasRelRole = false;
	
	public AuthorityRole() {
	}

	public AuthorityRole(Object id, Object name, Object code, Object authorityDesc, Object showOrder,
			Object pid, Object roleId) {
		this.id = id != null ? Long.parseLong(id.toString()) : null;
		this.pid = pid != null ? Long.parseLong(pid.toString()) : null;
		this.name = name != null ? name.toString() : null;
		this.code = code != null ? code.toString() : null;
		this.authorityDesc = authorityDesc != null ? authorityDesc.toString() : null;
		this.showOrder = showOrder != null ? Integer.parseInt(showOrder.toString()): 1;
		this.roleId = roleId != null ? Long.parseLong(roleId.toString()) : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public boolean isHasRelRole() {
		if (roleId != null) {
			hasRelRole = true;
		}
		return hasRelRole;
	}

}
