package org.dante.springboot.listener;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staterwriter")
public class StateWritter {
	@Resource
	ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 将存活状态改为BROKEN（会导致kubernetes杀死pod）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/broken")
	public String broken() {
		AvailabilityChangeEvent.publish(applicationEventPublisher, StateWritter.this, LivenessState.BROKEN);
		return "success broken, " + new Date();
	}

	/**
	 * 将存活状态改为CORRECT
	 * 
	 * @return
	 */
	@RequestMapping(value = "/correct")
	public String correct() {
		AvailabilityChangeEvent.publish(applicationEventPublisher, StateWritter.this, LivenessState.CORRECT);
		return "success correct, " + new Date();
	}

	/**
	 * 将就绪状态改为REFUSING_TRAFFIC（导致kubernetes不再把外部请求转发到此pod）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/refuse")
	public String refuse() {
		AvailabilityChangeEvent.publish(applicationEventPublisher, StateWritter.this, ReadinessState.REFUSING_TRAFFIC);
		return "success refuse, " + new Date();
	}

	/**
	 * 将就绪状态改为ACCEPTING_TRAFFIC（导致kubernetes会把外部请求转发到此pod）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/accept")
	public String accept() {
		AvailabilityChangeEvent.publish(applicationEventPublisher, StateWritter.this, ReadinessState.ACCEPTING_TRAFFIC);
		return "success accept, " + new Date();
	}
}
