package org.dante.springboot.showcase.config;

import org.dante.springboot.showcase.action.FooAction;
import org.dante.springboot.showcase.eum.Events;
import org.dante.springboot.showcase.eum.States;
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
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine
public class ShowCaseConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowCaseConfig.class);
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().listener(listener());
	}
	
	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states
			.withStates()
				.initial(States.S0, fooAction())
				.state(States.S0)
			.and()
			.withStates()
				.parent(States.S0)
				.initial(States.S1)
				.state(States.S1)
				.and()
				.withStates()
					.parent(States.S1)
					.initial(States.S11)
					.state(States.S11)
					.state(States.S12)
					.and()
			.withStates()
				.parent(States.S0)
				.state(States.S2)	// 根 state 下只能有一个初始 sub state，本例是 States.S1
				.and()
				.withStates()
					.parent(States.S2)
					.initial(States.S21)
					.state(States.S21)
					.and()
					.withStates()
						.parent(States.S21)
						.initial(States.S211)
						.state(States.S211)
						.state(States.S212);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
	        .withExternal()
	            .source(States.S1).target(States.S1).event(Events.A)
	            .guard(foo1Guard())
	            .and()
	            .withExternal()
	                .source(States.S1).target(States.S11).event(Events.B)
	                .and()
	            .withExternal()
	                .source(States.S21).target(States.S211).event(Events.B)
	                .and()
	            .withExternal()
	                .source(States.S1).target(States.S2).event(Events.C)
	                .and()
	            .withExternal()
	                .source(States.S2).target(States.S1).event(Events.C)
	                .and()
	            .withExternal()
	                .source(States.S1).target(States.S0).event(Events.D)
	                .and()
	            .withExternal()
	                .source(States.S211).target(States.S21).event(Events.D)
	                .and()
	            .withExternal()
	                .source(States.S0).target(States.S211).event(Events.E)
	                .and()
	            .withExternal()
	                .source(States.S1).target(States.S211).event(Events.F)
	                .and()
	            .withExternal()
	                .source(States.S2).target(States.S11).event(Events.F)
	                .and()
	            .withExternal()
	                .source(States.S11).target(States.S211).event(Events.G)
	                .and()
	            .withExternal()
	                .source(States.S211).target(States.S0).event(Events.G)
	                .and()
	            .withInternal()
	                .source(States.S0).event(Events.H)
	                .guard(foo0Guard())
	                .action(fooAction())
	                .and()
	            .withInternal()
	                .source(States.S2).event(Events.H)
	                .guard(foo1Guard())
	                .action(fooAction())
	                .and()
	            .withInternal()
	                .source(States.S1).event(Events.H)
	                .and()
	            .withExternal()
	                .source(States.S11).target(States.S12).event(Events.I)
	                .and()
	            .withExternal()
	                .source(States.S211).target(States.S212).event(Events.I)
	                .and()
	            .withExternal()
	                .source(States.S12).target(States.S212).event(Events.I);
        ;
	}
	
	
	@Bean
	public FooAction fooAction() {
	    return new FooAction();
	}
	
	@Bean
	public FooGuard foo0Guard() {
	    return new FooGuard(0);
	}

	@Bean
	public FooGuard foo1Guard() {
	    return new FooGuard(1);
	}
	
	
	private static class FooGuard implements Guard<States, Events> {

	    private final int match;

	    public FooGuard(int match) {
	        this.match = match;
	    }

	    @Override
	    public boolean evaluate(StateContext<States, Events> context) {
	        Object foo = context.getExtendedState().getVariables().get("foo");
	        return !(foo == null || !foo.equals(match));
	    }
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
}
