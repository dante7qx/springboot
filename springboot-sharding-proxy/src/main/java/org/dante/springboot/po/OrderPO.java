package org.dante.springboot.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_order")
public class OrderPO {
	
	// id一般要使用分布式主键id来通过代码来生成
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private Long userId;
	
	private String status;
	
}
