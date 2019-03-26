package org.dante.springboot.service.impl;

import java.util.List;

import org.dante.springboot.dao.OrderDAO;
import org.dante.springboot.dao.OrderItemDAO;
import org.dante.springboot.dto.OrderDTO;
import org.dante.springboot.dto.OrderItemDTO;
import org.dante.springboot.po.OrderItemPO;
import org.dante.springboot.po.OrderPO;
import org.dante.springboot.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderItemDAO orderItemDAO;

	@Override
	@Transactional
	public void saveOrder(int count) {
		for (int i = 0; i< count; i++) {
			Long userId = Long.valueOf(i);
			OrderPO order = new OrderPO();
			order.setUserId(userId);
			order.setStatus("FINISHED");
			orderDAO.save(order);
			
			OrderItemPO orderItem = new OrderItemPO();
			orderItem.setOrder(order);
			orderItem.setUserId(userId);
			orderItemDAO.save(orderItem);
		}
	}

	@Override
	public List<OrderDTO> findOrders() {
		List<OrderDTO> orders = Lists.newArrayList();
		List<OrderPO> orderPOs = orderDAO.findAll();
		if(orderPOs == null) {
			return orders;
		}
		for (OrderPO orderPO : orderPOs) {
			OrderDTO order = new OrderDTO();
			BeanUtils.copyProperties(orderPO, order);
			orders.add(order);
		}
		return orders;
	}

	@Override
	public List<OrderItemDTO> findOrderItemByUserId(Long userId) {
		List<OrderItemPO> orderItemPOs = orderItemDAO.findByUserId(userId);
		List<OrderItemDTO> orderItems = Lists.newArrayList();
		for (OrderItemPO orderItemPO : orderItemPOs) {
			OrderItemDTO orderItem = new OrderItemDTO();
			BeanUtils.copyProperties(orderItemPO, orderItem);
			OrderDTO order = new OrderDTO();
			BeanUtils.copyProperties(orderItemPO.getOrder(), order);
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		return orderItems;
	}

	@Override
	public List<OrderItemDTO> findOrderItemInUserIds(List<Long> userIds) {
		List<OrderItemPO> orderItemPOs = orderItemDAO.findByUserIdIN(userIds);
		List<OrderItemDTO> orderItems = Lists.newArrayList();
		for (OrderItemPO orderItemPO : orderItemPOs) {
			OrderItemDTO orderItem = new OrderItemDTO();
			BeanUtils.copyProperties(orderItemPO, orderItem);
			OrderDTO order = new OrderDTO();
			BeanUtils.copyProperties(orderItemPO.getOrder(), order);
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		return orderItems;
	}
	
}
