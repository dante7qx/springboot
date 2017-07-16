package org.dante.springboot.springbootjwtserver.dao;

import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDAO extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO>{
	
	public UserPO findByUserName(String userName) throws Exception;
}
