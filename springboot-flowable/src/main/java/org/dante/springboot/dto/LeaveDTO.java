package org.dante.springboot.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LeaveDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 提交类型 I: 保存不提交 A: 提交
	 * 
	 */
	private String operType;
	private Long leaveId;
	private String procInstId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date endTime;
	private String leaveReason;

}
