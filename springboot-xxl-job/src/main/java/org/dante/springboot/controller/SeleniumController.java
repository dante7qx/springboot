package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SeleniumController {

	private int count = 0;
	
	@GetMapping("/selenium/views")
	public String views() {
		String result = "/selenium/views 访问次数 -> " + count++;
		log.info(result);
		return result;
	}
	
}
