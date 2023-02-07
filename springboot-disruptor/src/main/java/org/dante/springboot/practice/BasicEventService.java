package org.dante.springboot.practice;

public interface BasicEventService {
	
	/**
     * 发布一个事件
     * @param value
     * @return
     */
    void publish(String value);

    /**
     * 返回已经处理的任务总数
     * @return
     */
    long eventCount();
	
}
