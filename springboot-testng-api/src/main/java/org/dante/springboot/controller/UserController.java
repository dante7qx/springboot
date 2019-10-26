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
@RequestMapping("/user")
public class UserController {

	@GetMapping("/g-msg/{msg}")
	public String getMsg(@PathVariable String msg) {
		String returnStr = "GET user /msg => ".concat(msg);
		log.info(returnStr);
		return returnStr;
	}
	
	@PostMapping("/p-msg")
	public String postMsg(@RequestBody MsgVO msg) {
		String returnStr = "POST user /msg => ".concat(msg.toString());
		log.info(returnStr);
		return returnStr;
	}
	
	@DeleteMapping("/d-msg/{msgId}")
	public String deleteMsg(@PathVariable String msgId) {
		String returnStr = "DElETE user /msg => ".concat(msgId);
		log.info(returnStr);
		return returnStr;
	}
	
}
