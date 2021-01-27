package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.GitDocChapterPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GitDocChapterDAO extends JpaRepository<GitDocChapterPO, String> {

	@Query("SELECT d FROM GitDocChapterPO d WHERE d.level = 1 and d.gitDocId = ?1 order by d.seq asc")
	public List<GitDocChapterPO> findRoot(String gitDocId);
	
	@Query("SELECT d FROM GitDocChapterPO d WHERE  d.pid = ?1 order by d.seq asc")
	public List<GitDocChapterPO> findByPid(String pid);
	
	@Modifying
	@Query("delete from GitDocChapterPO d where d.gitDocId=:gitDocId")
	public void deleteByGitDocId(@Param("gitDocId") String gitDocId);
}
