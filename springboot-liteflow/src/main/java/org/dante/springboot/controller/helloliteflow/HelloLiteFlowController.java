package org.dante.springboot.controller.helloliteflow;

import org.dante.springboot.service.helloliteflow.HelloLiteFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloliteflow")
public class HelloLiteFlowController {
	
	private final HelloLiteFlowService helloLiteFlowService;

	@Autowired
	public HelloLiteFlowController(HelloLiteFlowService helloLiteFlowService) {
		this.helloLiteFlowService = helloLiteFlowService;
	}
	
	@GetMapping
	public void sayHelloLiteFlow() {
		helloLiteFlowService.sayHelloLiteFlow();
	}
	
}
