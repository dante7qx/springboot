package org.dante.springboot.dao;

import org.dante.springboot.po.NotePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteDAO extends JpaRepository<NotePO, Long> {

}
