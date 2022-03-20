package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.DeployProcessPO;
import org.dante.springboot.vo.NativeSQLVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeployProcessDAO extends JpaRepository<DeployProcessPO, Long> {

	@Query(value = "select t.deploy_process_id as id, t.process_name as name from t_deploy_process t;", nativeQuery = true)
	public List<NativeSQLVO> xx();

}
