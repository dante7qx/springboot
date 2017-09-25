package org.dante.springboot.action.config;

import org.dante.springboot.eum.Events;
import org.dante.springboot.eum.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class ActionConfig extends EnumStateMachineConfigurerAdapter<States, Events>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionConfig.class);
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states
        .withStates()
            .initial(States.S1, action())
            .state(States.S1, action(), null)
            .state(States.S2, null, action())
            .state(States.S2, action())
            .stateExit(States.S3, action(), action());
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
		.withExternal().source(States.S1).target(States.S3).event(Events.E1);
	}
	
	@Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
            	LOGGER.info("当前执行 {}。", context.getStage().name());
            }
        };
    }

}
