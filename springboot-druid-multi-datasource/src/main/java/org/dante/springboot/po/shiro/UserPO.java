package org.dante.springboot.po.shiro;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "T_User")
@Data
public class UserPO {
	
	@Id
	private Long id;
	private String userName;
	private String password;
}
