package org.dante.springboot.util;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;
import org.springframework.scheduling.support.CronSequenceGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CronSequenceGeneratorTest {
	
	@Test
	public void calDateByCron() {
		String cron = "0 */5 * * * ?"; // 每个五分钟执行一次

		CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);

		Date currentTime = Date.from(Instant.now());

		log.info("当前时间: {}", currentTime);

		Date nextTimePoint = cronSequenceGenerator.next(currentTime); // currentTime为计算下次时间点的开始时间
		log.info("下次执行时间: {}", nextTimePoint);

		Date nextNextTimePoint = cronSequenceGenerator.next(nextTimePoint);

		log.info("下下次执行时间 {}", nextNextTimePoint);
	}

	@Test
	public void getCronByDate() {
		String cron = QuartzCronDateUtils.getCron(Date.from(Instant.now()));
		log.info("当前时间Cron表达式：{}", cron);
	}
}
