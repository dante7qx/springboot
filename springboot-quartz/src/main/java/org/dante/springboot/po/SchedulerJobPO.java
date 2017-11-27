package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_scheduler_job")
public class SchedulerJobPO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String jobId;
	private String jobName;
	private String jobClass;
	private String jobDesc;
	private Date previousFireTime;
	private Date fireTime;
	private Date nextFireTime;
	private Date startTime;
	private String cron;
	private Boolean startJob;
	private String failReason;
	private Date updateDate;
}
