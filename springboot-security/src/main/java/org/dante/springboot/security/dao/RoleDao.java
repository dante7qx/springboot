package org.dante.springboot.security.dao;

import org.dante.springboot.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>{

}
