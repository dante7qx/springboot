package org.dante.springboot.helloworld;

import org.dante.springboot.eum.Events;
import org.dante.springboot.eum.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class HelloWorldApp implements CommandLineRunner {
	
	@Autowired
	private StateMachine<States, Events> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		stateMachine.sendEvent(Events.E1);
	    stateMachine.sendEvent(Events.E2);
	    stateMachine.sendEvent(Events.E3);
		
	}
}
