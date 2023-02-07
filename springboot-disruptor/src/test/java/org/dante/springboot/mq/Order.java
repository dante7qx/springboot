package org.dante.springboot.mq;

import lombok.Data;

@Data
public class Order {
	
	private int seq;
	
	private String orderNo;
	
	private String orderDetail;
	
	private Double price;
	
}
