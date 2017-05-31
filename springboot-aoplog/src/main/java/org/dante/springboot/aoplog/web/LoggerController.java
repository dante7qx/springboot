package org.dante.springboot.aoplog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

	@GetMapping("/logger/{id}")
	public String logger(@PathVariable Integer id) {
		String returnStr = "";

		switch (id) {
		case 1:
			returnStr = "info";
			break;
		case 2:
			returnStr = "warning";
			break;
		case 3:
			returnStr = "error";
			break;
		default:
			break;
		}
		return "Logger -> " + returnStr;
	}

}
