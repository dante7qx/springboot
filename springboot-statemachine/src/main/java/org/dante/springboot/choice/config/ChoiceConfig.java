package org.dante.springboot.choice.config;

import java.util.EnumSet;

import org.dante.springboot.eum.Events;
import org.dante.springboot.eum.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;


/**
 * 条件判断状态
 * 
 * 1. 必须在 states 和 transitions 同时定义
 * 2. choice() 用于定义 state，并且需要制定 source state
 * 3. transitions 使用 withChoice() -> source -> first/then/last(if/elseif/else) -> end
 * 
 * 
 * @author dante
 *
 */
@Configuration
@EnableStateMachine
public class ChoiceConfig extends EnumStateMachineConfigurerAdapter<States, Events>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChoiceConfig.class);
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().autoStartup(true);
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states
	        .withStates()
	            .initial(States.S0)
	            .choice(States.S1)
	            .end(States.S2)
	            .states(EnumSet.allOf(States.class));
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal().
				source(States.S0).
				target(States.S1).
				event(Events.E1)
			.and()
        	.withChoice()
	            .source(States.S1)
	            .first(States.S2, c -> {
	            	LOGGER.info("进入此状态"); 	// lambda 表达式
                    return true;
                }, c -> {
	            	LOGGER.info("当前动作：{}", c.getMessage());
	            })
	            .then(States.S3, s3Guard())
	            .last(States.S4);
	}
	
	@Bean
    public Guard<States, Events> s3Guard() {
        return new Guard<States, Events>() {
            @Override
            public boolean evaluate(StateContext<States, Events> context) {
            	LOGGER.info("跳出此状态！");
                return false;
            }
        };
    }
}
