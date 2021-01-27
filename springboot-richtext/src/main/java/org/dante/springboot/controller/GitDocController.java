package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.service.GitDocService;
import org.dante.springboot.vo.GitDocVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/git")
public class GitDocController {

	@Autowired
	private GitDocService gitDocService;
	
	@PostMapping("/doc_titles")
	public List<GitDocVO> findGitDocs() {
		return gitDocService.findGitDocs();
	}
	
	@PostMapping("/persist_doc_title")
	public GitDocVO persist(@RequestBody GitDocVO vo) {
		return gitDocService.persist(vo);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable String id) {
		gitDocService.deleteById(id);
	}
	
	
}
