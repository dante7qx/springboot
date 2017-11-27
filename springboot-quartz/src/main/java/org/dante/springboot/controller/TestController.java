package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.po.TestPO;
import org.dante.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;

	@GetMapping("/all")
	public List<TestPO> queryTests() {
		return testService.findTests();
	}
	
}
