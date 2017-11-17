package org.dante.springboot.dao.springboot;

import org.dante.springboot.po.springboot.PersonPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDAO extends JpaRepository<PersonPO, Long> {

}
