package org.dante.springboot.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronExpression;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CronSequenceGeneratorTest {
	
	/**
	 * Spring5.3 改动：https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
	 * 
	 */
	@Test
	public void calDateByCron() {
		String cron = "0 */5 * * * ?"; // 每个五分钟执行一次

		CronExpression expression = CronExpression.parse(cron);

		LocalDateTime currentTime = LocalDateTime.now();

		log.info("当前时间: {}", currentTime);

		LocalDateTime nextTimePoint = expression.next(LocalDateTime.now()); // currentTime为计算下次时间点的开始时间
		log.info("下次执行时间: {}", nextTimePoint);

		LocalDateTime nextNextTimePoint = expression.next(nextTimePoint);

		log.info("下下次执行时间 {}", nextNextTimePoint);
	}

	@Test
	public void getCronByDate() {
		String cron = QuartzCronDateUtils.getCron(Date.from(Instant.now()));
		log.info("当前时间Cron表达式：{}", cron);
	}
}
