package org.dante.springboot.entryexit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class EntryExitApp implements CommandLineRunner {
	
	@Autowired
	private StateMachine<String, String> stateMachine;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(EntryExitApp.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		stateMachine.sendEvent("E1");
		stateMachine.sendEvent("ENTRY");
		stateMachine.sendEvent("EXIT");
	}
	
}
