package org.dante.springboot.po;

import java.io.Serializable;
import java.util.Date;

public class TeacherPO implements Serializable {
	private static final long serialVersionUID = 3991721714575978116L;
	
	private Long id;
	private String name;
	private String post;
	private Date updateDate;

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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
