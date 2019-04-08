package org.dante.springboot.dao;

import org.dante.springboot.po.OrderPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<OrderPO, Long> {
	
}
