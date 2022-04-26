package org.dante.springboot.genoffice.controller;

import org.dante.springboot.genoffice.thymeleaf.ThymeleafPDFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PDFController {
	
private final ThymeleafPDFUtil thymeleafWordUtil;
	
	public PDFController(@Autowired ThymeleafPDFUtil thymeleafWordUtil) {
		this.thymeleafWordUtil = thymeleafWordUtil;
	}

}
