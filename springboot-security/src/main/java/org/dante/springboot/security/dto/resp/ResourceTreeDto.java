package org.dante.springboot.security.dto.resp;

import java.util.List;

import com.google.common.collect.Lists;

public class ResourceTreeDto {
	private Long id;
	private String text;
	private String state = "open";
	private List<ResourceTreeDto> children;
	private ResourceTreeAttribute attributes;
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
	public List<ResourceTreeDto> getChildren() {
		if(children == null) {
			children = Lists.newArrayList();
		}
		return children;
	}
	public void setChildren(List<ResourceTreeDto> children) {
		this.children = children;
	}
	public ResourceTreeAttribute getAttributes() {
		return attributes;
	}
	public void setAttributes(ResourceTreeAttribute attributes) {
		this.attributes = attributes;
	}
	
}
