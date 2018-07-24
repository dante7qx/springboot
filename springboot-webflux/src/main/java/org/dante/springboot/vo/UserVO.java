package org.dante.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

	private String name;
	private int age;
	private String email;
	
}
