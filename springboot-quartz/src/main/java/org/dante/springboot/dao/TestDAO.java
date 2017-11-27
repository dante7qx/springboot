package org.dante.springboot.dao;

import org.dante.springboot.po.TestPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDAO extends JpaRepository<TestPO, Long> {

}
