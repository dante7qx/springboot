package org.dante.springboot.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Long orderItemId;
	private Long userId;
	private OrderDTO order;
}
