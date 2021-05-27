package org.dante.springboot.listener;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AvailabilityListener {

	/**
     * 监听系统消息，
     * AvailabilityChangeEvent类型的消息都从会触发此方法被回调
     * @param event
     */
    @EventListener
    public void onStateChange(AvailabilityChangeEvent<? extends AvailabilityState> event) {
        log.info(event.getState().getClass().getSimpleName() + " : " + event.getState());
    }
	
}
