package org.dante.springboot.dao;

import org.dante.springboot.po.TagPO;
import org.springframework.data.repository.CrudRepository;

public interface TagDAO extends CrudRepository<TagPO, Long> {

}
