package org.dante.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpiritScheduler {

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private static final String JOB_PACKAGE = "org.dante.springboot.quartz.job.";
	private static final String JOB_GROUP = "SPIRIT_JOB_GROUP";
	private static final String TRIGGER_GROUP = "SPIRIT_TRIGGER_GROUP";

	public void startJob(String cron, String group, String jobId, String job) {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			Class jobClass = Class.forName(JOB_PACKAGE + job);
			// 创建jobDetail实例，绑定Job实现类
			// 指明job的名称，所在组的名称，以及绑定job类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobId, group).build();// 设置Job的名字和组
			// jobDetail.getJobDataMap().put("name","MyName");//动态添加数据

			// corn表达式 每2秒执行一次
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

			// 设置定时任务的时间触发规则
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobId, group).startNow()
					.withSchedule(scheduleBuilder).build();

			// 把作业和触发器注册到任务调度中
			scheduler.scheduleJob(jobDetail, cronTrigger);
			if(!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
