package org.dante.springboot.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SchedulerDTO {
	
	private Long id;
	private String jobId;
	private String jobName;
	private String jobClass;
	private String jobDesc;
	private String cron;
	private Boolean startJob;
	private Date startTime;
	private Date previousFireTime;
	private Date fireTime;
	private Date nextFireTime;
	private Date updateDate;
	
}
