package org.dante.springboot.controller;

import java.text.NumberFormat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class SysController {

	@GetMapping("/sys")
	public Mono<String> docker() {
		Runtime runtime = Runtime.getRuntime();
		final NumberFormat format = NumberFormat.getInstance();
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024 * 1024;
		final String mega = " MB";
		StringBuilder sb = new StringBuilder();
		sb.append("========================== Memory Info ==========================").append("<br>")
				.append("Free memory: " + format.format(freeMemory / mb) + mega).append("<br>")
				.append("Allocated memory: " + format.format(allocatedMemory / mb) + mega).append("<br>")
				.append("Max memory: " + format.format(maxMemory / mb) + mega).append("<br>")
				.append("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega)
				.append("<br>").append("=================================================================");

		log.info(sb.toString().replace("<br>", "/n"));
		log.info("=================================================================\n");

		return Mono.just(sb.toString());
	}

}
