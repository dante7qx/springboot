package org.dante.springboot.genoffice.controller;

import java.io.IOException;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.genoffice.poitl.PoiTlUtil;
import org.dante.springboot.util.NanoIdUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/word")
public class WordController {
	
	@RequestMapping("/poitl/download")
	public void downloadWithPoiTl(HttpServletResponse response) throws IOException {
	    final Map<String, Object> map = Maps.newHashMap();
	    map.put("content", "Object 是 JavaScript 的一种 数据类型 。它用于存储各种键值集合和更复杂的实体。Objects 可以通过 Object() 构造函数或者使用 对象字面量 的方式创建。");
	    
	    RowRenderData userRow0 = Rows.of("编号", "姓名", "年龄").textColor("FFFFFF")
	    	      .bgColor("4472C4").center().create();
	    TableRenderData userTable = Tables.create(userRow0);
	    IntStream.range(1, 10).forEach(i -> {
	    	userTable.addRow(Rows.create(NanoIdUtils.randomNanoId(), "用户【"+i+"】", (23 + i) + ""));
	    });
	    map.put("userTable", userTable);
	    
	    
	    PoiTlUtil.createWord(map, "demo1.docx", "", "测试Poi-Tl.docx", response);
	}
	
}
