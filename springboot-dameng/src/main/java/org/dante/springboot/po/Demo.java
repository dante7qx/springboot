package org.dante.springboot.po;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 业务对象 t_demo
 * 
 * @author sunchao
 */
@Data
public class Demo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 业务主键ID */
	private Long demoId;

	/** 业务名称 */
	private String demoName;

	/** 业务时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date demoTime;

	/** 业务图片 */
	private String demoImage;

	/** 业务附件 */
	private String attachment;

	/** 业务内容 */
	private String demoContent;

	/** 删除标识 0 未删除 1 已删除 */
	private Integer delFlag;

	/** 创建者 */
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** 更新者 */
	private String updateBy;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/** 请求参数 */
	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

}
