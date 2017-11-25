package org.dante.springboot.controller;

import org.dante.springboot.quartz.SpiritScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class QuartzController {
	
	@Autowired
	private SpiritScheduler spiritScheduler;
	
	@GetMapping("/add_job")
	public String addJob(String groupId, String jobId) {
		log.info("添加任务 {} - {}", groupId, jobId);
		spiritScheduler.startJob("0/5 * * * * ?", groupId, jobId, "HelloJob");
		return "ok!";
	}
	
	
	
}
