package org.dante.springboot.timer;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnEventNotAccepted;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine(id = "TimerConfigStateMachine")
public class ContextIntegration {

	@OnStateChanged
	public void onStateChange(@EventHeaders Map<String, Object> headers, ExtendedState extendedState,
			StateMachine<String, String> stateMachine, Message<String> message, Exception e) {
		System.out.println("***********************************************");
		System.out.println("Headers: " + headers);
		System.out.println("ExtendedState: " + extendedState);
		System.out.println("StateMachine: " + stateMachine);
		System.out.println("Message: " + message);
		System.out.println("Exception: " + e);
		System.out.println("***********************************************");
		
	}

	@OnTransition(source = "S1", target = "S3")
	public void transition(StateContext<String, String> stateContext) {
		System.out.println("ContextIntegration 拦截到 Transition: S1 -> S3, 触发事件：" + stateContext.getEvent());
	}
	
	@OnEventNotAccepted(event = "E2")
	public void eventNotAccept() {
		System.out.println("Event E1 没有被接受！");
	}

}
