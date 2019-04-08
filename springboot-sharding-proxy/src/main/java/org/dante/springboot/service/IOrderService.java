package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dto.OrderDTO;
import org.dante.springboot.dto.OrderItemDTO;

public interface IOrderService {
	
	/**
	 * 创建订单的数量
	 * 
	 * @param i
	 */
	public void saveOrder(int count);
	
	public List<OrderDTO> findOrders();
	
	public List<OrderItemDTO> findOrderItemByUserId(Long userId);
	
	public List<OrderItemDTO> findOrderItemInUserIds(List<Long> userIds);
}
