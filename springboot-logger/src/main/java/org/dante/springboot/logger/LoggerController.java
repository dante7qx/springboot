package org.dante.springboot.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

	private final static Logger logger = LoggerFactory.getLogger(LoggerController.class);

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
		logger.info("request id {}", id);
		logger.warn("request id {}", id);
		logger.error("request id {}", id);
		return "Logger ->" + returnStr;
	}

}
