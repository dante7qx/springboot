package org.dante.springboot.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;

	private Long userId;
	
	private String status; 

}
