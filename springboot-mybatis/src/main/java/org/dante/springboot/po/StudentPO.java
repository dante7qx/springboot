package org.dante.springboot.po;

import java.util.Date;

import org.dante.springboot.handler.CipherEncrypt;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StudentPO {

	private Long id;
	private CipherEncrypt name;
	private int age;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	
	private StudentAddressPO address;

}
