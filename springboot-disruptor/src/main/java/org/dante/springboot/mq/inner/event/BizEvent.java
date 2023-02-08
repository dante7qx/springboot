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
	
	/** 消息体*/
	private T payload;
	
	/**
	 * 清理业务数据
	 */
	public void clear() {
		payload = null;
	}

}
