package org.dante.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

/**
 * webflux整合Thymeleaf
 * 
 * https://blog.csdn.net/j903829182/article/details/80290583
 * 
 * @author dante
 *
 */
@Controller
public class ViewController {

	@GetMapping("/")
	public Mono<String> index() {
		return Mono.create(monoSink -> monoSink.success("index"));
	}
}
