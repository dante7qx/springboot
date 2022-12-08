package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * SVN 记录VO
 * 
 * @author dante
 *
 */
@Data
public class SvnRecordVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 提交人 */
	private String account;

	/** 版本号 */
	private long revision;
	
	/** 提交时间（格式：yyyy-MM-dd HH:mm:ss） */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date commitDate;
	
	/** 提交说明 */
	private String commitMessage;
	
	/** 代码行数 */
	private long totalCodeLine;

}
