package org.dante.springboot.kafka.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PubMsg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String msgId;
	private String msgDesc;
	private List<Msg> payload = new ArrayList<>();
}
