package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎API
 * 
 * @author dante
 *
 */
@RestController
public class HelloController {
	
	@GetMapping("/")
	public String hello() {
		return "你好，EasyPOI！";
	}
	
}
