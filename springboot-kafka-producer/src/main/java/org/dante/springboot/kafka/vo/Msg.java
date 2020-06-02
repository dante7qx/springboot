package org.dante.springboot.kafka.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Msg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
}
