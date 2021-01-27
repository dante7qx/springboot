package org.dante.springboot.dao;

import org.dante.springboot.po.GitDocPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitDocDAO extends JpaRepository<GitDocPO, String> {
	
}
