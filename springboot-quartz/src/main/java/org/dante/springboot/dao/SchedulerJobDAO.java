package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.SchedulerJobPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SchedulerJobDAO extends JpaRepository<SchedulerJobPO, Long>, JpaSpecificationExecutor<SchedulerJobPO> {
	
	public SchedulerJobPO findByJobId(String jobId);
	
	@Query("select s from SchedulerJobPO s where coalesce(startJob, 1) = 1")
	public List<SchedulerJobPO> findStartedJob();
	
}
