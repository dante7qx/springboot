package org.dante.springboot.quartz.listener;

import org.dante.springboot.quartz.util.ExceptionUtils;
import org.dante.springboot.service.SchedulerJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpiritJobListener extends JobListenerSupport implements ApplicationContextAware{
	
	private SchedulerJobService schedulerJobService;
	
	private ApplicationContext ctx;

	@Override
	public String getName() {
		return "SpiritJobListener";
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobId = context.getJobDetail().getKey().getName();
		String failReason = null;
		if(jobException != null) {
			failReason = ExceptionUtils.getStackMsg(jobException);
		}
		if(schedulerJobService == null) {
			schedulerJobService = (SchedulerJobService) ctx.getBean("schedulerJobService");
		}
		schedulerJobService.updateJob(jobId, context.getFireTime(), context.getPreviousFireTime(), context.getNextFireTime(), failReason);

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}
