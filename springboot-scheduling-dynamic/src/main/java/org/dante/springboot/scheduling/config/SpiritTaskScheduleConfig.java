package org.dante.springboot.scheduling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SpiritTaskScheduleConfig {
	
	@Bean("spiritPoolScheduler")
	public ThreadPoolTaskScheduler spiritPoolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("spiritPoolScheduler");
		scheduler.setPoolSize(20);
		return scheduler;
	}
}
