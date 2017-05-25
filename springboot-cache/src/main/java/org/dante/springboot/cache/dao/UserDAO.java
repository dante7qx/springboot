package org.dante.springboot.cache.dao;

import org.dante.springboot.cache.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserPO, Long> {

}
