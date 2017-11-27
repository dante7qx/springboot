package org.dante.springboot.quartz.job;

import java.time.Instant;
import java.util.Date;

import org.dante.springboot.po.TestPO;
import org.dante.springboot.quartz.annotation.SpiritQuartz;
import org.dante.springboot.service.TestService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

@SpiritQuartz(jobId = "testJob", jobName = "测试Quartz定时任务")
public class TestJob implements SpiritJob {
	
	@Autowired
	private TestService testService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TestPO test = new TestPO();
		test.setName("测试"+ (int)(Math.random()*3+1)*100);
		test.setUpdateDate(Date.from(Instant.now()));
		testService.persist(test);
	}

	
}
