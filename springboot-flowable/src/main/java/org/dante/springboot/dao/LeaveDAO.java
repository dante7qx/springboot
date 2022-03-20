package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.LeavePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaveDAO extends JpaRepository<LeavePO, Long> {
	
	@Query("select t from LeavePO t where t.userId = :userId order by t.createTime desc")
	public List<LeavePO> findByUserId(String userId);
	
	public List<LeavePO> findByUserIdOrderByCreateTimeDesc(String userId);
}
