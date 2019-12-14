package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthController {

	@GetMapping("/health")
	public String health() {
		if(log.isDebugEnabled()) {
			log.debug("Health check...");
		}
		return "up";
	}

}
