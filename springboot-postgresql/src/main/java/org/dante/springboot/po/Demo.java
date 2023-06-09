package org.dante.springboot.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 业务对象 t_demo
 * 
 * @author sunchao
 * @date 2022-07-30
 */
@Data
@TableName("t_demo")
public class Demo {

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
	
}
