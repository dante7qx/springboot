package org.dante.springboot.mq.inner.event;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务事件消息
 * 
 * @author dante
 *
 */
@Data
@NoArgsConstructor
public class BizEvent<T> {
	
	/** 消息业务类型 */
	private String type;
	
	/** 消息体*/
	private T payload;

}
