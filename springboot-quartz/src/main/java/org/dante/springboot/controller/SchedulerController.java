package org.dante.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dante.springboot.dto.SchedulerDTO;
import org.dante.springboot.quartz.factory.SpiritSchedulerService;
import org.dante.springboot.quartz.util.QuartzAnnotationUtils;
import org.dante.springboot.service.SchedulerJobService;
import org.dante.springboot.vo.QueryVO;
import org.dante.springboot.vo.SchedulerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                        new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
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
	
	@PostMapping("/update_job")
	public SchedulerDTO updateJob(SchedulerDTO schedulerDTO) {
		schedulerJobService.persistJob(schedulerDTO);
		return schedulerDTO;
	}
	
	
	
}
