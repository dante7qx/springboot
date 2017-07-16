package org.dante.springboot.springbootjwtserver.dao;

import org.dante.springboot.springbootjwtserver.po.RolePO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<RolePO, Long> {

}
