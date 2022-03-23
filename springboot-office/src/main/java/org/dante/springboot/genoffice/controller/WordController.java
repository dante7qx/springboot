package org.dante.springboot.genoffice.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.genoffice.freemarker.FreemarkerWordUtil;
import org.dante.springboot.genoffice.poitl.PoiTLUtil;
import org.dante.springboot.genoffice.thymeleaf.ThymeleafWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

@RestController
@RequestMapping("/word")
public class WordController {
	
	private final ThymeleafWordUtil thymeleafWordUtil;
	
	public WordController(@Autowired ThymeleafWordUtil thymeleafWordUtil) {
		this.thymeleafWordUtil = thymeleafWordUtil;
	}
	
	@RequestMapping("/freemarker/download")
	public void downloadWithFreemarker(HttpServletResponse response) throws IOException {
	    Map<String, Object> map = Maps.newHashMap();
	    Map<String, Object> data = Maps.newHashMap();
	    data.put("content", "Object 是 JavaScript 的一种 数据类型 。它用于存储各种键值集合和更复杂的实体。Objects 可以通过 Object() 构造函数或者使用 对象字面量 的方式创建。");
	    map.put("data", data);
	    map.put("age", 20);
	    map.put("sex","男");
	    FreemarkerWordUtil.createWord(map, "test1.xml", null, "测试文件[Freemarker]", response);
	}
	
	@RequestMapping("/thymeleaf/download")
	public void downloadWithThymeleaf(HttpServletResponse response) throws IOException {
	    Map<String, Object> map = Maps.newHashMap();
	    Map<String, Object> data = Maps.newHashMap();
	    data.put("content", "Object 是 JavaScript 的一种 数据类型 。它用于存储各种键值集合和更复杂的实体。Objects 可以通过 Object() 构造函数或者使用 对象字面量 的方式创建。");
	    map.put("data", data);
	    map.put("age", 20);
	    map.put("sex","男");
	    thymeleafWordUtil.createWord(map, "test2.xml", "", "测试文件[Thymeleaf]", response);
	}
	
	@RequestMapping("/poitl/download")
	public void downloadWithPoiTl(HttpServletResponse response) throws IOException {
	    Map<String, Object> map = Maps.newHashMap();
	    Map<String, Object> data = Maps.newHashMap();
	    data.put("content", "Object 是 JavaScript 的一种 数据类型 。它用于存储各种键值集合和更复杂的实体。Objects 可以通过 Object() 构造函数或者使用 对象字面量 的方式创建。");
	    map.put("data", data);
	    map.put("age", 20);
	    map.put("sex","男");
	    PoiTLUtil.createWord(map, "test1.docx", "", "测试Poi-Tl.docx", response);
	}
	
}
