package org.dante.springboot.security.dao;

import org.dante.springboot.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
	public User findByAccount(String account) throws Exception;
	
}
