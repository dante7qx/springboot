package org.dante.springboot.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("T_Hobby")
public class HobbyPO {

	@TableId(type = IdType.AUTO)
	private Long id;
	
	@TableField(exist = false)
	private String personName;
	
	private String hobby;
	
	@TableField("UpdateBy")
	private String updateBy;
	
	@TableField("UpdateDate")
	private Date updateDate;
	
	@TableField("IsDelete")
	private Boolean isDelete;
	
	@TableField(exist = false)
	private PersonPO person;
}
