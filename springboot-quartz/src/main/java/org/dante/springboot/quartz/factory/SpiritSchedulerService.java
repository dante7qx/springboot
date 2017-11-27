package org.dante.springboot.quartz.factory;

import java.util.Date;

import org.dante.springboot.quartz.listener.SpiritJobListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class SpiritSchedulerService {

	public static final String JOB_PACKAGE = "org.dante.springboot.quartz.job";
	private static final String JOB_GROUP = "SPIRIT_JOB_GROUP";
	private static final String TRIGGER_GROUP = "SPIRIT_TRIGGER_GROUP";
	
	@Autowired
	@Qualifier("spiritSchedulerFactory")
	private SchedulerFactoryBean schedulerFactory;
	@Autowired
	private SpiritJobListener spiritJobListener;

	/**
	 * 新增定时任务（默认启动）
	 * 
	 * @param jobId
	 * @param jobClazz
	 * @param cron
	 * @param startTime
	 */
	public void addJob(String jobId, String jobClazz, String cron, Date startTime) {
		addJob(jobId, jobClazz, cron, startTime, true);
	}
	
	/**
	 * 新增定时任务
	 * 
	 * @param jobId
	 * @param jobClazz
	 * @param cron
	 * @param startTime
	 * @param startJob
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addJob(String jobId, String jobClazz, String cron, Date startTime, boolean startJob) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			Class jobClass = Class.forName(jobClazz);
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobId, JOB_GROUP).build();// 设置Job的名字和组
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			triggerBuilder.withIdentity(jobId, TRIGGER_GROUP);
			if(startTime == null || new Date().compareTo(startTime) > 0) {
				triggerBuilder.startNow();
			} else {
				triggerBuilder.startAt(startTime);
			}
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.getListenerManager().addJobListener(spiritJobListener);
			if(!startJob) {
				pauseJob(jobId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param jobId
	 * @param cron
	 * @param startTime
	 */
	public void updateJobCron(String jobId, String cron, Date startTime) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobId, TRIGGER_GROUP);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (! oldTime.equalsIgnoreCase(cron)) {
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				triggerBuilder.withIdentity(jobId, TRIGGER_GROUP);
				if(startTime == null || new Date().compareTo(startTime) > 0) {
					triggerBuilder.startNow();
				} else {
					triggerBuilder.startAt(startTime);
				}
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				trigger = (CronTrigger) triggerBuilder.build();
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 移除指定jobId的定时任务
	 * 
	 * @param jobId
	 */
	public void removeJob(String jobId) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobId, TRIGGER_GROUP);
			scheduler.pauseTrigger(triggerKey);
			scheduler.unscheduleJob(triggerKey);
			scheduler.deleteJob(JobKey.jobKey(jobId, JOB_GROUP));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 暂停指定jobId的定时任务
	 * 
	 * @param jobId
	 */
	public void pauseJob(String jobId) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobId, JOB_GROUP);  
			scheduler.pauseJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 恢复指定jobId的定时任务
	 * 
	 * @param jobId
	 */
	public void resumeJob(String jobId) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobId, JOB_GROUP);  
			scheduler.resumeJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 启动所有定时任务
	 */
	public void startJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public void shutdownJobs() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
