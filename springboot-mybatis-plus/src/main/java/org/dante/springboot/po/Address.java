package org.dante.springboot.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_address")
public class Address {
	
	private Long id;
    private Long empId;
    private String city;
    private String address;
	
}
