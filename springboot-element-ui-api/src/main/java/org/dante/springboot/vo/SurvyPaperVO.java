package org.dante.springboot.vo;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurvyPaperVO {

	private Long id;
	
	private String type;

	private Map<String, Object> paperInfo;
	
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date createTime;

	private String updateBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
	private Date updateTime;
	
	public SurvyPaperVO(Long id) {
		this.id = id;
	}
}
