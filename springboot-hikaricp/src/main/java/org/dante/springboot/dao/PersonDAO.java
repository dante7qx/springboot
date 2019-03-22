package org.dante.springboot.dao;

import org.dante.springboot.po.PersonPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDAO extends JpaRepository<PersonPO, Long> {

}
