package org.dante.springboot.cache.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String account;
	private String name;
	private int age;
	private BigDecimal balance;
	private Date updateDate;
	
	public UserVO(String account, String name, int age, BigDecimal balance) {
		super();
		this.account = account;
		this.name = name;
		this.age = age;
		this.balance = balance;
		this.updateDate = Date.from(Instant.now());
	}

}
