package org.dante.springboot.cache.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class UserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String account;
	private String name;
	private int age;
	private BigDecimal balance;
	private Date updateDate;

}
