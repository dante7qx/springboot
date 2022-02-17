package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class MsgDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String msgId;
	private String msg;
	private String sendTime;
}
