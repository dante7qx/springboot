package org.dante.springboot.vo;

import lombok.Data;

@Data
public class BookVo {

	// 图书的基本字段
	private Long id;
	private Long accountId;
	private String title;
	private String content;

	// 用户表的字段
	private String userName;
	private int userAge;

}
