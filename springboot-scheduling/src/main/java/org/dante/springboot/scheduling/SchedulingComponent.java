package org.dante.springboot.scheduling;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring Task 定时任务，简化版的Cron
 * 
 * cron表达式的时间是以服务器的时间判断的，不是以程序启动的时间判断 
 * （服务器时间：14:56秒，那程序启动后4秒就会执行每1分钟执行一次的任务）
 * 
 * @author dante
 *
 */
@Component
@EnableScheduling
public class SchedulingComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingComponent.class);

	/**
	 * 每10s执行一次，
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void process10s() {
		LOGGER.info("process10s -> {}", Date.from(Instant.now()));
	}

	/**
	 * 每1min执行一次
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void process1min() {
		LOGGER.info("process1min -> {}", Date.from(Instant.now()));
	}
	
	/**
	 * 当一次方法执行完毕之后，延迟5秒再执行该方法
	 */
	@Scheduled(initialDelay = 30000L, fixedDelay = 5000L)
	public void fixDelay() {
		LOGGER.info("fixDelay -> {}", Date.from(Instant.now()));
	}
	
	/**
	 * 每隔20秒执行一次该方法
	 */
	@Scheduled(fixedRate = 20000L)
	public void fixRate() {
		LOGGER.info("fixRate -> {}", Date.from(Instant.now()));
	}
	
}
