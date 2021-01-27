package org.dante.springboot.service;

import java.util.List;
import java.util.UUID;

import org.dante.springboot.SpringbootRichTextEditorApplicationTests;
import org.dante.springboot.vo.GitDocVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GitDocServiceTests extends SpringbootRichTextEditorApplicationTests {
	
	@Autowired
	private GitDocService gitDocService;
	
	@Test
	public void gitDocLifecyle() {
		GitDocVO vo1 = new GitDocVO();
		vo1.setId(UUID.randomUUID().toString().toLowerCase());
		vo1.setTitle("分布式研发");
		gitDocService.persist(vo1);
		
		GitDocVO vo2 = new GitDocVO();
		vo2.setId(UUID.randomUUID().toString().toLowerCase());
		vo2.setTitle("大前端 - 数据埋点");
		gitDocService.persist(vo2);
		
		List<GitDocVO> gitDocs = gitDocService.findGitDocs();
		Assert.notEmpty(gitDocs, "数据未成功插入数据库！");
		log.info("GitDocs -> {}", gitDocs);
		
		gitDocService.deleteById(vo1.getId());
		
		List<GitDocVO> gitDocs2 = gitDocService.findGitDocs();
		Assert.isTrue(gitDocs2.size() == 1, "删除不成功！");
		log.info("GitDocs2 -> {}", gitDocs);
		
	}
	

}
