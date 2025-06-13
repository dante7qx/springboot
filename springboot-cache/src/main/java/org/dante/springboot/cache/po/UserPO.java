package org.dante.springboot.cache.po;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;



@Entity
@Table(name="t_user")
@Data
public class UserPO implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String account;
	private String name;
	private int age;
	private BigDecimal balance;
	private Date updateDate;

	public UserPO() {
	}
	
	public UserPO(String account, String name, int age, BigDecimal balance) {
		this.account = account;
		this.name = name;
		this.age = age;
		this.balance = balance;
		this.updateDate = Date.from(Instant.now());
	}

	@Override
	public String toString() {
		return "UserPO [id=" + id + ", account=" + account + ", name=" + name + ", age=" + age + ", balance=" + balance
				+ ", updateDate=" + updateDate + "]";
	}

}
