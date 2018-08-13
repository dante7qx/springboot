package org.dante.springboot.security.dao;

import java.util.List;

import org.dante.springboot.security.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceDao extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource>{
	
	@Query("select r from Resource r where r.parentResource.id is null order by r.showOrder asc")
	public List<Resource> findRootResource();
	
	@Query("select r from Resource r where r.parentResource.id =:pid order by r.showOrder asc")
	public List<Resource> findByPid(@Param("pid") Long pid);
	
	@Query("select distinct r.parentResource.id from Resource r where r.parentResource.id is not null")
	public List<Long> findAllParentId();
}
