package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 查询参数VO
 * 
 * @author dante
 *
 */
@Data
public class SvnQueryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 
	 * 项目SVN地址 
	 * 
	 * @required
	 */
	private String svnUrl;
	
	/** 
	 * 开始时间（格式：yyyy-MM-dd）
	 * 
	 * @required
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	/** 
	 * 结束时间（格式：yyyy-MM-dd）
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

}
