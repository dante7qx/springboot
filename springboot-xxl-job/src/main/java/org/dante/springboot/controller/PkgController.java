package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PkgController {

	@GetMapping("/pkg/{arg}")
	public String pkg(@PathVariable String arg) {
		log.info("Req Pkg {}", arg);
		return "pkg - ".concat(arg);
	}

}
