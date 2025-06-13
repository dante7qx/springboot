package org.dante.springboot.dao;

import org.dante.springboot.po.TagPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<TagPO, Long> {

}
