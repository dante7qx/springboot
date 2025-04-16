package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class HealthCheckController {
	
	@GetMapping("/health_check")
	public String hello() {
		return "up";
	}
	
}
