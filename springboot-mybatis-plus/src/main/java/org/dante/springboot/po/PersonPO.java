package org.dante.springboot.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("T_Person")
public class PersonPO {

	@TableId(type = IdType.AUTO)
	private Long id;

	private String name;

	private int age;

	private String address;

	@TableField("UpdateBy")
	private String updateBy;

	@TableField("UpdateDate")
	private Date updateDate;

	public PersonPO() {
	}

	public PersonPO(Long id) {
		this.id = id;
	}
}
