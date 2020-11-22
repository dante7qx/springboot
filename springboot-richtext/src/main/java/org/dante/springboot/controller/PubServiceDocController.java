package org.dante.springboot.controller;

import org.dante.springboot.service.PubServiceDocService;
import org.dante.springboot.vo.PubServiceDocVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/doc")
public class PubServiceDocController {

	@Autowired
	private PubServiceDocService pubServiceDocService;
	
	@PostMapping("/persist")
	public PubServiceDocVO persist(@RequestBody PubServiceDocVO vo) {
		return pubServiceDocService.persist(vo);
	}
	
	@GetMapping("/find_by_id/{id}")
	public PubServiceDocVO findById(@PathVariable String id) {
		log.info("服务 {} 请求获取文档信息。。。。。");
		return pubServiceDocService.findPubServiceDocById(id);
	}
	
}
