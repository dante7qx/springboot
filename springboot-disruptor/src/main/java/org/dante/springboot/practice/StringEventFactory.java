package org.dante.springboot.practice;

import com.lmax.disruptor.EventFactory;

/**
 * 事件工厂（让 Disruptor 知道如何在内存中创建一个事件实例）
 * 
 * @author dante
 *
 */
public class StringEventFactory implements EventFactory<StringEvent> {
	
	@Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
	
}
