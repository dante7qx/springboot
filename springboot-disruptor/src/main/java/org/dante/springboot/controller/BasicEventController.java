package org.dante.springboot.controller;

import java.time.LocalDateTime;

import org.dante.springboot.practice.BasicEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicEventController {

	@Autowired
	private BasicEventService basicEventService;

	@GetMapping("/str/{value}")
	public String publish(@PathVariable("value") String value) {
		basicEventService.publish(value);
		return "消费完成, " + LocalDateTime.now().toString();
	}

}
