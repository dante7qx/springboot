package org.dante.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

@Controller
public class HelloController {

	@RequestMapping(value = "/hello")
	public ModelAndView index(ModelAndView modelAndView) {
		List<String> userList = Lists.newArrayList();
		userList.add("admin");
		userList.add("user1");
		userList.add("user2");

		modelAndView.addObject("userList", userList);
		modelAndView.addObject("x", 123456);
		modelAndView.setViewName("hello");
		return modelAndView;
	}
}
