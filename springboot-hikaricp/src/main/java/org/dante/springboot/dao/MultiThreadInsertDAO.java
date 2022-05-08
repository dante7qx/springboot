package org.dante.springboot.dao;

import org.dante.springboot.po.MultiThreadInsertPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiThreadInsertDAO extends JpaRepository<MultiThreadInsertPO, Long> {

}
