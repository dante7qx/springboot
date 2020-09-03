package org.dante.springboot.po;

import java.util.Date;

import lombok.Data;

@Data
public class StudentPO {

	private Long id;
	private String name;
	private int age;
	private Date updateDate;
	private StudentAddressPO address;

}
