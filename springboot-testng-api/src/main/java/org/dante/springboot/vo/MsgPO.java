package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MsgPO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String msgId;
	private String msg;
	private Date sendTime;

}
