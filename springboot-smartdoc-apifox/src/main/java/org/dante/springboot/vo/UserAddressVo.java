package org.dante.springboot.vo;

import lombok.Data;

/**
 * 用户地址
 * 
 * @author dante
 *
 */
@Data
public class UserAddressVo {
	/**
	 * 地址Id
	 */
	private Long addressId;
	
	/**
	 * 地址详情
	 */
	private String addressDetail;
}
