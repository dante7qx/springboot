package org.dante.springboot.security.dto.resp;

import java.util.List;

import org.dante.springboot.security.domain.Authority;

import com.google.common.collect.Lists;

public class AuthorityTreeDto {

	private Long id;
	private String text;
	private String iconCls;
	private String state = "closed";
	private List<AuthorityTreeDto> children;
	private Authority attributes;

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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<AuthorityTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<AuthorityTreeDto> children) {
		this.children = children;
	}

	public Authority getAttributes() {
		return attributes;
	}

	public void setAttributes(Authority attributes) {
		this.attributes = attributes;
	}

	public void addChildren(AuthorityTreeDto child) {
		if (this.children == null) {
			this.children = Lists.newArrayList();
		}
		this.children.add(child);
	}

}
