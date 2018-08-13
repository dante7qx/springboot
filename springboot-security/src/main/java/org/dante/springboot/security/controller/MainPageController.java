package org.dante.springboot.security.controller;

import java.util.List;

import org.dante.springboot.security.dto.resp.ResourceTreeDto;
import org.dante.springboot.security.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
	
	@Autowired
	private ResourceService resourceService;
	
	@PreAuthorize("hasAuthority('public')")
	@RequestMapping("/mainpage")
	public String index(Model model) {
		List<ResourceTreeDto> menus = resourceService.findUserResourceTree();
		model.addAttribute("menus", menus);
		return "mainpage/navtop";
	} 
}
