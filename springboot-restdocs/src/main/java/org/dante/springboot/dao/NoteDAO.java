package org.dante.springboot.dao;

import org.dante.springboot.po.NotePO;
import org.springframework.data.repository.CrudRepository;

public interface NoteDAO extends CrudRepository<NotePO, Long> {

}
