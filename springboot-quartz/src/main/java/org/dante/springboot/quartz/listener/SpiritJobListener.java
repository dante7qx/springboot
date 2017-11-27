package org.dante.springboot.quartz.listener;

import org.dante.springboot.quartz.util.ExceptionUtils;
import org.dante.springboot.service.SchedulerJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpiritJobListener implements JobListener {
	
	@Autowired
	private SchedulerJobService schedulerJobService;

	@Override
	public String getName() {
		return "SpiritJobListener";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String jobId = context.getJobDetail().getKey().getName();
		String failReason = null;
		if(jobException != null) {
			failReason = ExceptionUtils.getStackMsg(jobException);
		}
		schedulerJobService.updateJob(jobId, context.getFireTime(), context.getPreviousFireTime(), context.getNextFireTime(), failReason);

	}

}
