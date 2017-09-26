package org.dante.springboot.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class TimerApp implements CommandLineRunner {
	
	@Autowired
	private StateMachine<String, String> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(TimerApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		stateMachine.start();
//		stateMachine.sendEvent("E1");
		stateMachine.sendEvent("E2");
	}
}
