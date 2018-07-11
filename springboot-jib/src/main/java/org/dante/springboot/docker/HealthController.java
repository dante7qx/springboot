package org.dante.springboot.docker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthController {
	
	@GetMapping("/healthz")
	public String health() {
		try {
			Thread.sleep(1000L);
			log.info("请求开始进行健康检查......");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
}
