package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userName;
	private String groupId;
	private String imgBase64;

}
