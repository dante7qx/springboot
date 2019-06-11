package org.dante.springboot.tomcatlog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgVO {

	private String msgId;
	private String msgName;
	private long createTime;
	private String reve_to;
	
	public MsgVO() {}
	
}
