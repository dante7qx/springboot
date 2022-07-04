package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserBorrowRecord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String userName;
	private Integer userAge;
	private String bookName;
	private String bookNo;
	private Date borrowTime;
}
