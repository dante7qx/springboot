package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "T_Person")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class PersonPO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private int age;
	
	private String address;
	
	private String updateBy;
	
	private Date updateDate;
	
	public PersonPO() {
	}
	
	public PersonPO(Long id) {
		this.id = id;
	}
}
