package org.dante.springboot.dao;

import org.dante.springboot.po.SchedulerJobPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulerJobDAO extends JpaRepository<SchedulerJobPO, Long>, JpaSpecificationExecutor<SchedulerJobPO> {
	public SchedulerJobPO findByJobId(String jobid);
	
}
