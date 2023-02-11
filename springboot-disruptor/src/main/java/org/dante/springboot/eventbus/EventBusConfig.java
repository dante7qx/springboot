package org.dante.springboot.eventbus;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Configuration
public class EventBusConfig {
	
	@Autowired
	private EventBusListener eventBusListener;
	
	@Bean
	public EventBus eventBus() {
		EventBus eventBus = new EventBus();
		eventBus.register(eventBusListener);
		return eventBus;
	}
	
	@Bean
	public AsyncEventBus asyncEventBus() {
		AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
		eventBus.register(eventBusListener);
		return eventBus;
	}

}
