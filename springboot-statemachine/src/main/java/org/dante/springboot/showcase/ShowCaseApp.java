package org.dante.springboot.showcase;

import org.dante.springboot.showcase.eum.Events;
import org.dante.springboot.showcase.eum.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class ShowCaseApp implements CommandLineRunner {
	
	@Autowired
	private StateMachine<States, Events> stateMachine;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ShowCaseApp.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		stateMachine.start();
		stateMachine.sendEvent(Events.A);
		stateMachine.sendEvent(Events.C);
		stateMachine.sendEvent(Events.H);
		stateMachine.sendEvent(Events.C);
		stateMachine.sendEvent(Events.A);
	}

}
