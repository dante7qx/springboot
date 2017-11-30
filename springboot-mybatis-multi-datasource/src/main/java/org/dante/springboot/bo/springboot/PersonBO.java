package org.dante.springboot.bo.springboot;

import java.util.Date;

import lombok.Data;

@Data
public class PersonBO {
	private Long id;
	private String name;
	private int age;
	private String address;
	private String updateBy;
	private Date updateDate;
}
