package org.dante.springboot.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoVO {
	private Integer id;
    private String name;
    private String sex;
    private Integer age;
}
