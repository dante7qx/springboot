package org.dante.springboot.vo;

import lombok.Data;

/**
 * 联系方式
 * 
 * @author dante
 *
 */
@Data
public class UserContactVo {
	
	/**
	 * 联系方式Id
	 */
	private Long contactId;
	
	/**
	 * 联系类型（1 手机 2 固定电话）
	 */
	private Integer type;
	
	/**
	 * 手机号
	 */
	private String phoneNumber;
	
	/**
	 * 固定电话
	 */
	private String mobileNumber;
}
