package org.dante.springboot.po.springboot;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "T_Person")
@Data
public class PersonPO {
	
	@Id
	private Long id;

	private String name;
	
	private int age;
	
	private String address;
	
	private String updateBy;
	
	private Date updateDate;
}
