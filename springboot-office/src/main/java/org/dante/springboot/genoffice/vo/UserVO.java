package org.dante.springboot.genoffice.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String name;
	private int age;
}
