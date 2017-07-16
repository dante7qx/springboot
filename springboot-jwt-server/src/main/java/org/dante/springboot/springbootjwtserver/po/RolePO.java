package org.dante.springboot.springbootjwtserver.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_role")
@Data
public class RolePO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	
	public RolePO() {
	}
	
	public RolePO(Long id) {
		this.id = id;
	}
	
	
}
