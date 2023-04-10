package org.dante.springboot.controller;

import org.dante.springboot.service.helloliteflow.HelloLiteFlowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/liteflow")
public class LiteFlowController {
	
	private final HelloLiteFlowService helloLiteFlowService;

	public LiteFlowController(HelloLiteFlowService helloLiteFlowService) {
		this.helloLiteFlowService = helloLiteFlowService;
	}
	
	@GetMapping("/hello")
	public void sayHelloLiteFlow() throws Exception {
		helloLiteFlowService.sayHelloLiteFlow();
	}
	
}
