package org.dante.springboot.entryexit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class EntryExistConfig extends StateMachineConfigurerAdapter<String, String> {
	
	@Override
	public void configure(StateMachineConfigBuilder<String, String> config) throws Exception {
		super.configure(config);
	}
	
	@Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
        .withStates()
            .initial("S1")
            .state("S2")
            .state("S3")
            .and()
            .withStates()
                .parent("S2")
                .initial("S21")
                .entry("S2ENTRY")
                .exit("S2EXIT")
                .state("S22");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
        .withExternal()
            .source("S1").target("S2")
            .event("E1")
            .and()
        .withExternal()
            .source("S1").target("S2ENTRY")
            .event("ENTRY")
            .and()
        .withExternal()
            .source("S22").target("S2EXIT")
            .event("EXIT")
            .and()
        .withEntry()
            .source("S2ENTRY").target("S22")
            .and()
        .withExit()
            .source("S2EXIT").target("S3");
    }
	
}
