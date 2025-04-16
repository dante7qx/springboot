package org.dante.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SurvyPaper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String type;

	private JSONObject paperInfo;
	
	private String paperTitle;
	
	private Integer num;

	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date updateTime;
	
	
}
