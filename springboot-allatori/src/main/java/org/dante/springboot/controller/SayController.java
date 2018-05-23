package org.dante.springboot.controller;

import org.dante.springboot.vo.MsgVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayController {

	@GetMapping(value = "/hello")
	public String hello(MsgVO msgVo) {
		return "你好";
	}
	
	@GetMapping(value = "/bye/{msg}")
	public String sayBye(@PathVariable("msg") String msg) {
		return "你好, " + msg ;
	}
	
}
