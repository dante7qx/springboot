package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.entity.SurvyPaper;
import org.dante.springboot.entity.SurvyPaperUser;
import org.dante.springboot.service.SurvyPaperService;
import org.dante.springboot.vo.SurvyPaperVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.Console;

@RestController
@RequestMapping("/survy")
public class SurvyPaperController {
	
	private final SurvyPaperService survyPaperService;
	
	public SurvyPaperController(SurvyPaperService survyPaperService) {
		this.survyPaperService = survyPaperService;
	}
	
	/* ============================================= Mybatis ============================================= */
	@PostMapping("/list")
	public ResponseEntity<List<SurvyPaper>> getList(@RequestBody SurvyPaper survyPaper) {
		
		return ResponseEntity.ok(survyPaperService.findList(survyPaper));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SurvyPaper> getInfo(@PathVariable Long id) {
		return ResponseEntity.ok(survyPaperService.findById(id));
	}
	
	@PostMapping("/persist")
	public ResponseEntity<SurvyPaper> persist(@RequestBody SurvyPaper survyPaper) {
		Console.log(survyPaper);
		survyPaperService.persistSurvyPaper(survyPaper);
		return ResponseEntity.ok(survyPaper);
	}
	
	@PostMapping("/user_paper/{id}")
	public ResponseEntity<SurvyPaperUser> getUserPaper(@PathVariable Long id) {
		return ResponseEntity.ok(survyPaperService.findByPaperAndUserById(id));
	}
	
	@PostMapping("/user_paper/{paperId}/{userId}")
	public ResponseEntity<SurvyPaperUser> getUserPaper(@PathVariable Long paperId, @PathVariable Long userId) {
		return ResponseEntity.ok(survyPaperService.findByPaperAndUser(paperId, userId));
	}
	
	@PostMapping("/persist_user")
	public ResponseEntity<SurvyPaperUser> persistUser(@RequestBody SurvyPaperUser survyPaperUser) {
		survyPaperService.persistSurvyPaperUser(survyPaperUser);
		return ResponseEntity.ok(survyPaperUser);
	}
	
	/* ============================================= Spring Data JPA ============================================= */
	@GetMapping("/jpa/{id}")
	public ResponseEntity<SurvyPaperVO> getInfo2(@PathVariable Long id) {
		return ResponseEntity.ok(survyPaperService.getById(id));
	}
	
	@PostMapping("/jpa/delete/{id}")
	public ResponseEntity<SurvyPaperVO> persist(@PathVariable Long id) {
		return ResponseEntity.ok(survyPaperService.deleteSurvyPaper(id));
	}
	
	
	
}
