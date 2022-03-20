package org.dante.springboot.dao.springboot2;

import org.dante.springboot.po.springboot2.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserPO, Long> {

}
