package org.dante.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.service.I18nMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
	
	@Autowired
	private I18nMsgService i18nMsgService;

	/**
	 * 添加 Header 信息
	 * Accept-Language : zh-cn, zh, en-us
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/msg")
	public String msg(HttpServletRequest request) {
		return i18nMsgService.getMessage("welcome");
	}
	
	
}
