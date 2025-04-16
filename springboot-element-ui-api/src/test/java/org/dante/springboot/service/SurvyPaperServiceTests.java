package org.dante.springboot.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.dante.springboot.SpringbootElUIApiApplicationTests;
import org.dante.springboot.entity.SurvyPaper;
import org.dante.springboot.vo.SurvyPaperVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.lang.Console;

class SurvyPaperServiceTests extends SpringbootElUIApiApplicationTests {
	
	private final Long id = 0L;

	private final SurvyPaperService survyPaperService;
	
	SurvyPaperServiceTests(@Autowired SurvyPaperService survyPaperService) {
		this.survyPaperService = survyPaperService;
	}
	
	@Test
	void getById() {
		SurvyPaperVO vo = survyPaperService.getById(id);
		Console.log("【Spring data jpa】", vo);
		assertNotNull(vo, "【Spring data jpa】查询错误，无数据记录！");
	}
	
	@Test
	void findById() {
		SurvyPaper survyPaper = survyPaperService.findById(id);
		Console.log("【MyBatis】", survyPaper);
		assertNotNull(survyPaper, "【MyBatis】查询错误，无数据记录！");
	}
	
	@Test
	void findList() {
		List<SurvyPaper> list = survyPaperService.findList(new SurvyPaper());
		Console.log("【MyBatis】", list);
		assertNotEquals(0, list.size());
	}
}
