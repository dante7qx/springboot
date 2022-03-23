package org.dante.springboot.genoffice.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

@RestController
@RequestMapping("/freemarker")
public class FreemarkerWordController {
	
	@RequestMapping("/download")
	public void download(HttpServletResponse response) {
	    Map<String, Object> map = Maps.newHashMap();
	    map.put("name", "张三");
	    map.put("age", 20);
	    map.put("sex","男");
	    FreemarkerWordUtil.createWord(map, "template.ftl", "/templates/ftlh/", "测试文件", response);
	}
	
}
