package org.dante.springboot.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Value("${sec.kaptcha}")
	private Boolean kaptcha;
	
	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		model.addAttribute("kaptcha", kaptcha);
		return "login";
	}
	
}
