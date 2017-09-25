package org.dante.springboot.helloworld.config;

import java.util.EnumSet;

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
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineConfig.class);

	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().autoStartup(true).listener(listener());
	}

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates().initial(States.S0).states(EnumSet.allOf(States.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal().source(States.S0).target(States.S1).event(Events.E1)
			.and()
			.withExternal().source(States.S1).target(States.S2).event(Events.E2).guard(guard()).action(action())
			.and()
			.withExternal().source(States.S2).target(States.S3).event(Events.E3).action(action2(), errorAction());
	}

	@Bean
	public StateMachineListener<States, Events> listener() {
		return new StateMachineListenerAdapter<States, Events>() {
			@Override
			public void stateChanged(State<States, Events> from, State<States, Events> to) {
				LOGGER.info("状态从[ {} ] 变成 [ {} ]。", (from != null ? from.getId() : ""), to.getId());
			}
		};
	}

	@Bean
    public Guard<States, Events> guard() {
        return new Guard<States, Events>() {
            @Override
            public boolean evaluate(StateContext<States, Events> context) {
            	LOGGER.info("当前上下文[{}]", context.getMessage().toString());
                return true;
            }
        };
    }
	
	/**
	 * 状态变更索要执行的具体行为
	 * 
	 * @return
	 */
	@Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
            	LOGGER.info("状态变更，审批节点向下流转！");
            }
        };
    }
	
	@Bean
    public Action<States, Events> action2() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
            	throw new RuntimeException("自定义异常");
            }
        };
    }
	
	@Bean
    public Action<States, Events> errorAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext<States, Events> context) {
                // RuntimeException("MyError") added to context
                Exception exception = context.getException();
                exception.getMessage();
            }
        };
    }

}
