package org.dante.springboot.eventbus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Service
public class EventBusProducer<T> {

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private AsyncEventBus asyncEventBus;

	public void publishEvent(T event) {
		eventBus.post(event);
	}
	
	public void publishEventAsync(T event) {
		asyncEventBus.post(event);
	}
}
