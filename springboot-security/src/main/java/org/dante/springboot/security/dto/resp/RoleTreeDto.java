package org.dante.springboot.security.dto.resp;

import java.util.List;

public class RoleTreeDto {
	private Long id;
	private String text;
	private String state = "open";
	private List<RoleTreeDto> children;

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
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<RoleTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<RoleTreeDto> children) {
		this.children = children;
	}

}
