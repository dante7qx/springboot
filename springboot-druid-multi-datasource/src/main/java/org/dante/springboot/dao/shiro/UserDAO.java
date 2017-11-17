package org.dante.springboot.dao.shiro;

import org.dante.springboot.po.shiro.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserPO, Long> {

}
