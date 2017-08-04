package org.dante.springboot.cache.dao;

import org.dante.springboot.cache.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDAO extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO>{

	public UserPO findByAccount(String account);
	
}
