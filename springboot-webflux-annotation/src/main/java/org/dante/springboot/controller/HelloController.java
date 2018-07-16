package org.dante.springboot.controller;

import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
	
	@Value("${hello.msg}")
	private String hello;

	@GetMapping("/")
	public Mono<String> docker() {
		Runtime runtime = Runtime.getRuntime();
		final NumberFormat format = NumberFormat.getInstance();
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024 * 1024;
		final String mega = " MB";
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("========================== Memory Info ==========================").append("<br/>");
		strBuilder.append("Free memory: " + format.format(freeMemory / mb) + mega).append("<br/>");
		strBuilder.append("Allocated memory: " + format.format(allocatedMemory / mb) + mega).append("<br/>");
		strBuilder.append("Max memory: " + format.format(maxMemory / mb) + mega).append("<br/>");
		strBuilder.append("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega).append("<br/>");
		strBuilder.append("=================================================================").append("<br/>");

		return Mono.just(hello + "Docker！<br/>" + strBuilder);
	}

}
