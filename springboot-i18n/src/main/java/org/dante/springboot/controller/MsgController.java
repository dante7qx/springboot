package org.dante.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.service.I18nMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

	@Autowired
	private I18nMsgService i18nMsgService;

	/**
	 * Springboot I18N 国际化
	 * 
	 * @return
	 */
	@GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
	public String i18n() {
		return "<h1 align='center'>Springboot i18n 国际化</h1>";
	}

	/**
	 * 健康检查
	 * 
	 * @return
	 */
	@GetMapping("/healthz")
	public String healthz() {
		return "ok";
	}

	/**
	 * 添加 Header 信息 Accept-Language : zh-cn, zh, en-us
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/msg", produces = MediaType.APPLICATION_XML_VALUE)
	public String msg(HttpServletRequest request) {
		return "<i18n>"
					.concat(i18nMsgService.getMessage("welcome"))
					.concat(" - ")
					.concat(i18nMsgService.getMessage("country"))
					.concat("</i18n>");
	}

}
