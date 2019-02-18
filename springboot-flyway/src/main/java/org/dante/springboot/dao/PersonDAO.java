package org.dante.springboot.dao;

import org.dante.springboot.domain.PersonPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDAO extends JpaRepository<PersonPO, Long> {

}
