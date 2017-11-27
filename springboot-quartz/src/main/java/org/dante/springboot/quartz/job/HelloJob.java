package org.dante.springboot.quartz.job;

import java.util.Date;

import org.dante.springboot.quartz.annotation.SpiritQuartz;
import org.dante.springboot.quartz.util.DateUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpiritQuartz(jobId = "helloJob", jobName = "你好Quartz")
public class HelloJob implements SpiritJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date fireTime = context.getFireTime();
		long jobRunTime = context.getJobRunTime();
		Date previousFireTime = context.getPreviousFireTime();
		Date nextFireTime = context.getNextFireTime();
		Date scheduledFireTime = context.getScheduledFireTime();
		JobKey jobKey = context.getJobDetail().getKey();
		
		log.info("=====================================\n");
		log.info("JobName ==> {}\n", jobKey.getName());
		log.info("previousFireTime ==> {}\n", DateUtils.formatDateTime(previousFireTime));
		log.info("fireTime ==> {}\n", DateUtils.formatDateTime(fireTime));
		log.info("nextFireTime ==> {}\n", DateUtils.formatDateTime(nextFireTime));
		log.info("scheduledFireTime ==> {}\n", DateUtils.formatDateTime(scheduledFireTime));
		log.info("jobRunTime ==> {}\n", jobRunTime);
		log.info("=====================================");
	}

}
