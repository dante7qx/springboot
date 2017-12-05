package org.dante.springboot.scheduling.job;

import org.dante.springboot.scheduling.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloJob extends SpiritJob {

	@Autowired
	private HelloService helloService;
	
	@Override
	public void run() {
		helloService.sayHello();
	}

}
