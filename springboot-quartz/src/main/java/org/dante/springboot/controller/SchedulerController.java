package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dto.SchedulerDTO;
import org.dante.springboot.quartz.factory.SpiritSchedulerService;
import org.dante.springboot.quartz.util.QuartzAnnotationUtils;
import org.dante.springboot.service.SchedulerJobService;
import org.dante.springboot.vo.QueryVO;
import org.dante.springboot.vo.SchedulerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController extends BaseController {
	
	@Autowired
	private SchedulerJobService schedulerJobService;
	
	@PostMapping("/query_job")
	public List<SchedulerDTO> queryJob(QueryVO queryVO) {
		return schedulerJobService.queryJobs(queryVO.getQ());
	}
	
	@PostMapping("/query_combo")
	public List<SchedulerVO> queryCombo() {
		List<SchedulerVO> schedulerVOs = QuartzAnnotationUtils.getAnnotation(SpiritSchedulerService.JOB_PACKAGE);
		return schedulerVOs;
	}
	
	@PostMapping("/query_by_id")
	public SchedulerDTO queryById(Long id) {
		return schedulerJobService.queryById(id);
	}
	
	@PostMapping("/update_job")
	public SchedulerDTO updateJob(SchedulerDTO schedulerDTO) {
		schedulerJobService.persistJob(schedulerDTO);
		return schedulerDTO;
	}
	
	@PostMapping("/check_job_exist")
	public boolean checkJobExist(SchedulerDTO schedulerDTO) {
		Long id = schedulerDTO.getId();
		String jobId = schedulerDTO.getJobId();
		return schedulerJobService.checkExistJob(id, jobId);
	}
	
}
