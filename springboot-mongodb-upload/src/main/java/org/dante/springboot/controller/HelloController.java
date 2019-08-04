package org.dante.springboot.controller;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 基于注解的响应式
 * 
 * @author dante
 *
 */
@Slf4j
@RestController
public class HelloController {

	@Value("${hello.msg}")
	private String hello;

	@GetMapping("/runtime")
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
		log.info("One request coming...");
		return Mono.just(hello + "Docker！<br/>" + strBuilder);
	}
	
	@GetMapping("/flux")
	public Flux<String> flux() {
		return Flux.just("你好", "Spring WebFlux", "Reactor", "Netty")
					.map(x -> x.concat(" <br>"));
	}
	
	@GetMapping("/usr")
	public Mono<UserVO> getUser() {
		return Mono.just(new UserVO("但丁", 33, "ch.sun@haihangyun.com"));
	}
	
	@GetMapping("/usrs")
	public Flux<UserVO> getUsers() {
		return Flux.just(new UserVO("但丁", 33, "ch.sun@haihangyun.com"), new UserVO("Snake", 45, "snake@163.com"));
	}
	
	/**
	 * 基于 SpringMVC 的延迟API
	 * 
	 * @param latency
	 * @return
	 */
	@GetMapping("/delay-mvc/{latency}")
	public String delayWithMvc(@PathVariable long latency) {
		log.info("Request delay-mvc ...");
		try {
            TimeUnit.MILLISECONDS.sleep(latency);
        } catch (InterruptedException e) {
            return "Error during thread sleep";
        }
        return "基于 SpringMVC 的延迟 RestFul API";
	}

	/**
	 * 基于 SpringWebFlux 的延迟 RestFul API
	 * 
	 * @param latency
	 * @return
	 */
	@GetMapping("/delay-flux/{latency}")
	public Mono<String> delayWithFlux(@PathVariable long latency) {
		log.info("Request delay-flux ...");
		return Mono.just("基于 SpringWebFlux 的延迟API")
				.delayElement(Duration.ofMillis(latency));
	}
	
}
