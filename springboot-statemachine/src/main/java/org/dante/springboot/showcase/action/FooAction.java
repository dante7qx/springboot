package org.dante.springboot.showcase.action;

import java.util.Map;

import org.dante.springboot.showcase.eum.Events;
import org.dante.springboot.showcase.eum.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class FooAction implements Action<States, Events> {

	private static final Logger log = LoggerFactory.getLogger(FooAction.class);

	@Override
	public void execute(StateContext<States, Events> context) {
		Map<Object, Object> variables = context.getExtendedState().getVariables();
		Integer foo = context.getExtendedState().get("foo", Integer.class);
		if (foo == null) {
			log.info("初始化 [ foo to 0 ]");
			variables.put("foo", 0);
		} else if (foo == 0) {
			log.info("状态转移 [ foo to 1 ]");
			variables.put("foo", 1);
		} else if (foo == 1) {
			log.info("状态转移 [ foo to 0 ]");
			variables.put("foo", 0);
		}
	}

}
