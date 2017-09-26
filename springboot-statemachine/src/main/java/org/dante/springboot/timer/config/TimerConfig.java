package org.dante.springboot.timer.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.event.StateMachineEvent;

@Configuration
@EnableStateMachine(name = "TimerConfigStateMachine")
public class TimerConfig extends StateMachineConfigurerAdapter<String, String> {

	@Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
            .withStates()
                .initial("S1")
                .state("S2")
                .state("S3");
    }
	
	@Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
            .withExternal()
                .source("S1").target("S2").event("E1")
                .and()
            .withExternal()
                .source("S1").target("S3").event("E2")
                .and()
            .withInternal()
                .source("S2")
                .action(timerAction())
                .timer(5000)		// 每隔指定时间执行一次
                .and()
            .withInternal()
                .source("S3")
                .action(timerAction())
                .timerOnce(1000);	// 到达指定时间后，只执行一次
    }
	
	@Bean
    public Action<String, String> timerAction() {
		return new Action<String, String>() {
            @Override
            public void execute(StateContext<String, String> context) {
            	System.out.println("当前执行" + context.getSource().getId());
            }
        };
    }
	
	/**
	 * Event 监听器
	 * 
	 * @return
	 */
	@Bean
    public ApplicationListener<StateMachineEvent> contextListener() {
        return new ApplicationListener<StateMachineEvent>() {
			@Override
			public void onApplicationEvent(StateMachineEvent event) {
				System.out.println("当前事件：" + event.toString());
			}
        	
        };
    }
	
}
