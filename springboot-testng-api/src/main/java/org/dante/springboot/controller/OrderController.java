package org.dante.springboot.controller;

import org.dante.springboot.vo.MsgVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

	@GetMapping("/g-msg/{msg}")
	public String getMsg(@PathVariable String msg) {
		String returnStr = "GET order /msg => ".concat(msg);
		log.info(returnStr);
		return returnStr;
	}
	
	@PostMapping("/p-msg")
	public String postMsg(@RequestBody MsgVO msg) {
		String returnStr = "POST order /msg => ".concat(msg.toString());
		log.info(returnStr);
		return returnStr;
	}
	
	@DeleteMapping("/d-msg/{msgId}")
	public String deleteMsg(@PathVariable String msgId) {
		String returnStr = "DElETE order /msg => ".concat(msgId);
		log.info(returnStr);
		return returnStr;
	}
	
}
