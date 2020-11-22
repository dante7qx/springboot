package org.dante.springboot.dao;

import org.dante.springboot.po.PubServiceDocPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubServiceDocDAO extends JpaRepository<PubServiceDocPO, String> {

}
