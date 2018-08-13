package org.dante.springboot.security.dao;

import java.util.List;

import org.dante.springboot.security.dao.nativesql.AuthorityRoleNativeSql;
import org.dante.springboot.security.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
	
	@Query("select t from Authority t where t.parentAuthority.id is null order by t.showOrder asc")
	public List<Authority> findRootAuthority() throws Exception;
	
	@Query("select t from Authority t where t.parentAuthority.id = :pid order by t.showOrder asc")
	public List<Authority> findByParentId(@Param("pid") Long pid) throws Exception;
	
	@Query(value = AuthorityRoleNativeSql.FINDAUTHORITYROLEBYROLEID, nativeQuery = true)
	public List<Object[]> findAuthorityRoleByRoleId(Long roleId);
	
}
