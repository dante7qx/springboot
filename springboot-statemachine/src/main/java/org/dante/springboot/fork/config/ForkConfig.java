package org.dante.springboot.fork.config;


import org.dante.springboot.eum.Events;
import org.dante.springboot.eum.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * Fork 状态，即当前状态可进入多个状态（交叉）
 * 
 * 目标状态必须是一个根级状态 或者 
 * 
 * @author dante
 *
 */
@EnableStateMachine
@Configuration
public class ForkConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states
			.withStates()
		        .initial(States.S1)
		        .fork(States.S2)
		        .state(States.S3)
	        .and()
	        .withStates()
	            .parent(States.S3)
	            .initial(States.S21)
	            .state(States.S22)
	            .state(States.S23)
	            .end(States.S24)
	            .and()
	        .withStates()
	            .parent(States.S3)
	            .initial(States.S31)
	            .state(States.S31)
	            .state(States.S32)
	            .end(States.S33);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal().
				source(States.S1).
				target(States.S2).
				event(Events.E1)
			.and()
			.withFork()
		        .source(States.S2)
		        .target(States.S22)
		        .target(States.S32);
	}
}
