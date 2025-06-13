package org.dante.springboot.po;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "T_Person")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class PersonPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
