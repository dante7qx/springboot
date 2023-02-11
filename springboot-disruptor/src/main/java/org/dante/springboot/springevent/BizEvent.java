package org.dante.springboot.springevent;

import org.springframework.context.ApplicationEvent;

/**
 * ApplicationEvent：表示事件本身，自定义事件需要继承该类，用来定义事件
 * 
 * @author dante
 *
 */
public class BizEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public BizEvent(T event) {
		super(event);
	}

}
