package org.dante.springboot.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.dante.springboot.dao.SchedulerJobDAO;
import org.dante.springboot.dao.SchedulerJobSpecification;
import org.dante.springboot.dto.SchedulerDTO;
import org.dante.springboot.po.SchedulerJobPO;
import org.dante.springboot.quartz.factory.SpiritSchedulerService;
import org.dante.springboot.quartz.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SchedulerJobService {

	@Autowired
	private SchedulerJobDAO schedulerJobDAO;
	@Autowired
	private SpiritSchedulerService spiritSchedulerService;
	
	@PostConstruct
	public void startAllJobs() {
		log.info("================= 启动所有定时任务 ================");
		List<SchedulerJobPO> startJobs = schedulerJobDAO.findStartedJob();
		startJobs.stream().forEach(j -> {
			spiritSchedulerService.addJob(j.getJobId(), j.getJobClass(), j.getCron(), j.getStartTime());
		});
		spiritSchedulerService.startJobs();
		log.info("================= 启动所有定时任务 ================");
	}
	
	public List<SchedulerDTO> queryJobs(Map<String, Object> queryMap) {
		List<SchedulerJobPO> pos = schedulerJobDAO.findAll(SchedulerJobSpecification.querySpecification(queryMap));
		return pos.stream().map(p -> convertToDTO(p)).collect(Collectors.toList());
	}
	
	@Transactional
	public void persistJob(SchedulerDTO schedulerDTO) {
		SchedulerJobPO oldPO = schedulerJobDAO.findByJobId(schedulerDTO.getJobId());
		SchedulerJobPO po = convertToPO(schedulerDTO);
		boolean startJob = po.getStartJob().booleanValue();
		if(oldPO != null) {
			po.setId(oldPO.getId());
			if(startJob) {
				spiritSchedulerService.updateJobCron(po.getJobId(), po.getCron(), po.getStartTime());
			} else {
				spiritSchedulerService.pauseJob(po.getJobId());
			}
		} else {
			spiritSchedulerService.addJob(po.getJobId(), po.getJobClass(), po.getCron(), po.getStartTime(), startJob);
		}
		schedulerJobDAO.save(po);
	}
	
	@Transactional
	public void updateJob(String jobId, Date fireTime, Date previousFireTime, Date nextFireTime, String failReason) {
		SchedulerJobPO job = schedulerJobDAO.findByJobId(jobId);
		if(job == null) {
			return;
		}
		job.setFireTime(fireTime);
		if(previousFireTime != null) {
			job.setPreviousFireTime(previousFireTime);
		}
		job.setNextFireTime(nextFireTime);
		job.setFailReason(failReason);
		schedulerJobDAO.save(job);
	}
	
	@Transactional
	public void delete(Long id) {
		SchedulerJobPO po = schedulerJobDAO.findOne(id);
		if(po != null) {
			schedulerJobDAO.delete(id);
			spiritSchedulerService.removeJob(po.getJobId());
		}
	}
	
	public boolean checkExistJob(String jobId) {
		boolean exists = false;
		SchedulerJobPO schedulerJob = schedulerJobDAO.findByJobId(jobId);
		if(schedulerJob != null) {
			exists = true;
		}
		return exists;
	}
	
	private SchedulerJobPO convertToPO(SchedulerDTO schedulerDTO) {
		SchedulerJobPO schedulerJobPO = new SchedulerJobPO();
		BeanUtils.copyProperties(schedulerDTO, schedulerJobPO);
		if(schedulerJobPO.getStartTime() == null) {
			schedulerJobPO.setStartTime(DateUtils.currentDate());
		}
		return schedulerJobPO;
	}
	
	private SchedulerDTO convertToDTO(SchedulerJobPO schedulerJobPO) {
		SchedulerDTO schedulerDTO = new SchedulerDTO();
		BeanUtils.copyProperties(schedulerJobPO, schedulerDTO);
		return schedulerDTO;
	}
	
}
