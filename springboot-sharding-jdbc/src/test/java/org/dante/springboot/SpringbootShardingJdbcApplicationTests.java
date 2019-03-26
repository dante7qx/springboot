package org.dante.springboot;

import java.util.List;

import org.dante.springboot.dto.OrderItemDTO;
import org.dante.springboot.service.IOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootShardingJdbcApplicationTests {
	
	@Autowired
	private IOrderService orderService;
	
	@Test
	public void findOrderItemByUserId() {
		List<OrderItemDTO> orderItems = orderService.findOrderItemByUserId(2L);
		log.info("OrderItemDTO => {}", orderItems);
	}
}
