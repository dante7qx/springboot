package org.dante.springboot.po;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_order_item")
public class OrderItemPO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderPO order;
	
	private Long userId;
}
