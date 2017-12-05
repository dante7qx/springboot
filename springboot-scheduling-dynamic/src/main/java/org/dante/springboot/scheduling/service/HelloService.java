package org.dante.springboot.scheduling.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloService {
	
	public void sayHello() {
		log.info("{} -> {} 开始执行SayHello.", Date.from(Instant.now()), Thread.currentThread().getName());
	}
	
}
