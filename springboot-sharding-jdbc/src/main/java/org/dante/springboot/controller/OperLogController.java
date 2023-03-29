package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.entity.OperLogInline;
import org.dante.springboot.service.OperLogInlineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/oper_log")
@AllArgsConstructor
public class OperLogController {
	
	private final OperLogInlineService operLogService;
	
	@GetMapping("/add/{count}")
	public int add(@PathVariable Integer count) {
		return operLogService.insertOperlog(count);
	}
	
	@GetMapping("/batch_add/{count}")
	public int batchAdd(@PathVariable Integer count) {
		return operLogService.batchInsertOperlog(count);
	}
	
	@PostMapping("/list")
	public List<OperLogInline> list(@RequestBody OperLogInline operLog) {
		return operLogService.selectOperLogList(operLog);
	}
	
	@GetMapping("/clear")
	public void clear() {
		operLogService.cleanOperLog();
	}
	
	@GetMapping("/create")
	public void create() {
		operLogService.createTableIfNotExists();
	}
	
	@GetMapping("/drop")
	public void drop() {
		operLogService.cleanOperLog();
	}
	
	
}
