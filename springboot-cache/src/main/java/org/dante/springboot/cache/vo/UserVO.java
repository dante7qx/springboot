package org.dante.springboot.cache.vo;

import java.util.Date;
import java.util.List;

public class UserVO {
	private Long id;
	private String name;
	private Date birthday;
	private Double salary;
	private List<UserToolVO> tools;

	public UserVO() {
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public List<UserToolVO> getTools() {
		return tools;
	}

	public void setTools(List<UserToolVO> tools) {
		this.tools = tools;
	}

}
