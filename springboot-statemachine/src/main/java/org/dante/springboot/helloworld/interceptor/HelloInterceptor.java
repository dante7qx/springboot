package org.dante.springboot.helloworld.interceptor;

import org.dante.springboot.eum.Events;
import org.dante.springboot.eum.States;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 * 拦截器，和监听器的核心区别是，可以阻止 State Change、Transition
 * 
 * @return
 */
@Component
public class HelloInterceptor implements StateMachineInterceptor<States, Events>{

	@Override
	public Message<Events> preEvent(Message<Events> message, StateMachine<States, Events> stateMachine) {
		System.out.println("preEvent -->");
		return null;
	}

	@Override
	public void preStateChange(State<States, Events> state, Message<Events> message,
			Transition<States, Events> transition, StateMachine<States, Events> stateMachine) {
		System.out.println("preStateChange -->");
	}

	@Override
	public void postStateChange(State<States, Events> state, Message<Events> message,
			Transition<States, Events> transition, StateMachine<States, Events> stateMachine) {
		System.out.println("postStateChange -->");
	}

	@Override
	public StateContext<States, Events> preTransition(StateContext<States, Events> stateContext) {
		System.out.println("preTransition -->");
		return null;
	}

	@Override
	public StateContext<States, Events> postTransition(StateContext<States, Events> stateContext) {
		System.out.println("postTransition -->");
		return null;
	}

	@Override
	public Exception stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
		System.out.println("stateMachineError -->");
		return null;
	}

}
