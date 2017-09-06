package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSLController {

	@GetMapping("/ssl")
	public String ssl() {
		return "Hello SSL";
	}
	
}
