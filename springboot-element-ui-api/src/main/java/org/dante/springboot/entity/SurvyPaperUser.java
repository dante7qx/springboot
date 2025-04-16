package org.dante.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SurvyPaperUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long paperId;
	
	private Long userId;
	
	private String userName;
	
	private JSONObject answerInfo;

	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date updateTime;
	
}
