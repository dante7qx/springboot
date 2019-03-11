package org.dante.springboot.dao;

import org.dante.springboot.po.HobbyPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyDAO extends JpaRepository<HobbyPO, Long> {

}
