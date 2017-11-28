package org.dante.springboot.controller;

import java.util.Date;

import org.dante.springboot.quartz.util.SpecialDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		/**
		 * 1. 处理前端传递的各种日期类型：yyyy-MM-dd、yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm 等
		 * 2. 后端传递日期到前台：使用Jackson注解 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
		 */
		binder.registerCustomEditor(Date.class, new SpecialDateEditor());  
    }
}
