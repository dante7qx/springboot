package org.dante.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String account;
	
	private String name;
	
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
