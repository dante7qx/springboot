package org.dante.springboot.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_emp")
public class Emp {
	
	private Long id;
    private String name;
    private Integer age;
    private String email;
    
}
