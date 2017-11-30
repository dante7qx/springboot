package org.dante.springboot.bo.shiro;

import lombok.Data;

@Data
public class UserBO {
	
	private Long id;
	private String userName;
	private String password;
}
