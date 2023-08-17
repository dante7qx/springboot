package org.dante.springboot.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import lombok.Data;

@Data
@Table("tb_book")
public class Book {
	
	@Id(keyType = KeyType.Auto)
	private Integer id;
	
	private Integer accountId;
	
	private String title;
	
	private String content;
}
