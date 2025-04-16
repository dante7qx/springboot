package org.dante.springboot.dao;

import org.dante.springboot.po.DpCityCodePK;
import org.dante.springboot.po.DpCityCodePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DpCityCodeDAO extends JpaRepository<DpCityCodePO, DpCityCodePK>, JpaSpecificationExecutor<DpCityCodePO> {

	
	
	 @Query("SELECT dp FROM DpCityCodePO dp WHERE dp.pk.code = :code")
	 public DpCityCodePO findByPkCode(@Param("code") String code);
}

