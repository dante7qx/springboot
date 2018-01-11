package org.dante.springboot.po;

import lombok.Data;

@Data
public class ContactPO {
	
	private Long id;
	private Long userId;
	private String mobile;
	private String address;
	
}
