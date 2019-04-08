package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.OrderItemPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemDAO extends JpaRepository<OrderItemPO, Long> {
	public List<OrderItemPO> findByUserId(Long userId);
	
	@Query("select p from OrderItemPO p where p.userId in ?1 order by p.userId asc")
	public List<OrderItemPO> findByUserIdIN(List<Long> userIds);
	
}
