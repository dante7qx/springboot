package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dto.OrderDTO;
import org.dante.springboot.dto.OrderItemDTO;
import org.dante.springboot.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/create_order/{count}")
	public String createOrder(@PathVariable int count) {
		orderService.saveOrder(count);
		return "OK!";
	}
	
	@GetMapping("/orders")
	public List<OrderDTO> findOrders() {
		return orderService.findOrders();
	}
	
	@GetMapping("/order_item/{userId}")
	public List<OrderItemDTO> findOrderItemByUserId(@PathVariable Long userId) {
		return orderService.findOrderItemByUserId(userId);
	}
	
	@GetMapping("/order_item_in/{userIds}")
	public List<OrderItemDTO> findOrderItemByUserId(@PathVariable String userIds) {
		String[] userIdArr = userIds.split(",");
		List<Long> userIdList = Lists.newArrayList();
		for (String userId : userIdArr) {
			userIdList.add(Long.valueOf(userId));
		}
		return orderService.findOrderItemInUserIds(userIdList);
	}
	
	
}
