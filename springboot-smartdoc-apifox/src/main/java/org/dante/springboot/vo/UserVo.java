package org.dante.springboot.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 用户
 * 
 * @author dante
 *
 */
@Data
public class UserVo {
	
	/**
	 * 用户Id
	 */
	private Long userId;
	
	/**
	 * 姓名
	 * 
	 * @required
	 */
	private String userName;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 注册日期
	 */
	private Date setUpDate;
	
	/**
	 * 地址信息
	 */
	private UserAddressVo address;
	
	/**
	 * 联系方式
	 */
	private List<UserContactVo> contacts;
	
}
