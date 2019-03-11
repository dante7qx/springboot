package org.dante.springboot.po;

import java.util.Date;

import lombok.Data;

@Data
public class HobbyPO {
	
	private Long id;
	private String hobby;
	private String updateBy;
	private Boolean isDelete;
	private PersonPO person;
	private Date updateDate;
}
