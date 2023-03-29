package org.dante.springboot.service;

import org.dante.springboot.po.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 public int insertOrder() {
	    	for (int i = 1; i <= 60; i++) {
	    		OrderPO order = new OrderPO();
	            order.setUserId(i);
	            order.setOrderType(i % 2);
	            order.setAddressId(i);
	            order.setStatus("新增");
	            jdbcTemplate.update("insert into t_order(user_id, order_type, address_id, status) values (?, ?, ?, ?)", order.getUserId(), order.getOrderType(), order.getAddressId(), order.getStatus());
	    	}
	    	return 100;
	    }
}
