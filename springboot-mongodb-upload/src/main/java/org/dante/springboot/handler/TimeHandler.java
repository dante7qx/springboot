package org.dante.springboot.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TimeHandler {
	
	/**
	 * 获取日期
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getDate(ServerRequest request) {
		return ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(Mono.just(LocalDate.now().toString()).log(), String.class);
	}
	
	/**
	 * 获取时间
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getTime(ServerRequest request) {
		return ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(Mono.just(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))).log(), String.class);
	}
	
	/**
	 * 推送时间 
	 * 
	 * MediaType.TEXT_EVENT_STREAM  相当于 text/event-stream，即 SSE
	 * Flux.interval: 利用interval生成每秒一个数据的流
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> sendTimePerSec(ServerRequest request) {
		return ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(Flux.interval(Duration.ofSeconds(1L))
						.map(l -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))
								.concat(" - " + l)), 
						String.class);
	}
}
