package org.dante.springboot.controller;

import java.time.Duration;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
public class RSocketController {

	@MessageMapping("number.stream")
	public Flux<Integer> responseStream(Integer number) {
		log.info("number stream -> {}", number);
		return Flux.range(1, number)
			.delayElements(Duration.ofSeconds(1));
	}

	@MessageMapping("number.channel")
	public Flux<Long> biDirectionalStream(Flux<Long> numberFlux) {
		log.info("number channel -> {}", numberFlux);
		return numberFlux.map(n -> n * n)
			.onErrorReturn(-1L);
	}

}
