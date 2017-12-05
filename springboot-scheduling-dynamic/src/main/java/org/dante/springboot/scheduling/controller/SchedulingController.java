package org.dante.springboot.scheduling.controller;

import java.util.concurrent.ScheduledFuture;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.scheduling.job.HelloJob;
import org.dante.springboot.scheduling.util.SpringContextUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

@RestController
public class SchedulingController {

	@Autowired
	@Qualifier("spiritPoolScheduler")
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	private ScheduledFuture<?> future;

	@GetMapping("/start")
	public String startJob() {
		HelloJob helloJobBean = (HelloJob) SpringContextUtils.getBean(HelloJob.class);
		future = threadPoolTaskScheduler.schedule(helloJobBean, new CronTrigger("0/20 * * * * ?"));
		return "startJob";
	}

	@GetMapping("/stop")
	public String stopJob() {
		if (future != null) {
			future.cancel(true);
		}
		return "stopCron";
	}

	@GetMapping("/update")
	public String updateJob() {
		if (future != null) {
			future.cancel(true);
		}
		HelloJob helloJobBean = (HelloJob) SpringContextUtils.getBean(HelloJob.class);
		future = threadPoolTaskScheduler.schedule(helloJobBean, new CronTrigger("0/30 * * * * ?"));
		return "updateJob";
	}

	/**
	 * 获取ServletContext容器下的Bean
	 * 
	 * @param clazz
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	private <T> T getJob(Class<T> clazz, HttpServletRequest request) {
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return factory.getBean(clazz);
	}

}
