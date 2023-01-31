package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dante.springboot.annotation.EncryptField;

import lombok.Data;

@Entity
@Table(name = "T_Person")
@Data
public class PersonPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int age;

	@EncryptField
	private String address;

	private String updateBy;

	private Date updateDate;
}
