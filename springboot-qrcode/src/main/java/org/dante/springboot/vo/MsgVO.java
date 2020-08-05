package org.dante.springboot.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgVO {

	private String id;
	private String msg;
	private Date date;
	
}
