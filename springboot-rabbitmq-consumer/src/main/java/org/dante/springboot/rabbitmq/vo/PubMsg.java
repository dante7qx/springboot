package org.dante.springboot.rabbitmq.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PubMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String msgId;
	private String msgDesc;
	private List<Msg> payload;
}
