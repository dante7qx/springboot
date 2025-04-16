package org.dante.springboot.dao;

import org.dante.springboot.po.SurvyPaperPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurvyPaperDAO extends JpaRepository<SurvyPaperPO, Long> {

}
