package org.dante.springboot.security.dto.resp;

import java.util.List;

import org.dante.springboot.security.domain.AuthorityRole;
import org.springframework.util.CollectionUtils;

public class RoleAuthorityTreeDto {
	private Long id;
	private String text;
	private String state = "closed";
	private Long pid;
	private Boolean checked;
	private List<RoleAuthorityTreeDto> children;
	
	public RoleAuthorityTreeDto(AuthorityRole authorityRole) {
		this.id = authorityRole.getId();
		this.text = authorityRole.getName();
		this.state = authorityRole.getPid() == null ? "open": "closed";
		this.pid = authorityRole.getPid();
		this.checked = authorityRole.isHasRelRole();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		if(CollectionUtils.isEmpty(children)) {
			state = "open";
		}
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public List<RoleAuthorityTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<RoleAuthorityTreeDto> children) {
		this.children = children;
	}
	
}
