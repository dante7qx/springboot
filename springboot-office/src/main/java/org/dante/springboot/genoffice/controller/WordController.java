package org.dante.springboot.genoffice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.genoffice.poitl.PoiTlUtil;
import org.dante.springboot.util.NanoIdUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.google.common.collect.Lists;
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
	    String resume = "2011.04.01--2017.04.13 汉中移动有限公司 书记_NEWLINE"
	    		+ "2011.04.01--2017.04.13 汉中移动有限公司 书记_NEWLINE"
	    		+ "2022.06.06--2022.06.22 甘肃睿阳科技有限公司 技术员_NEWLINE"
	    		+ "2022.06.21--2022.07.29 甘肃移动分公司股份有限责任公司 技术员兼分管领导";
	    map.put("resume", resume.replaceAll("_NEWLINE", "\n"));
	    map.put("dataList", Lists.newArrayList("2011.04.01--2017.04.13 汉中移动有限公司 书记", "2011.04.01--2017.04.13 汉中移动有限公司 书记", "2022.06.06--2022.06.22 甘肃睿阳科技有限公司 技术员", "2022.06.21--2022.07.29 甘肃移动分公司股份有限责任公司 技术员兼分管领导"));
	    
	    // 图片
	    PictureRenderData pictureRenderData = Pictures.ofLocal("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/resources/static/img/C1.jpg").size(120, 120).create();
	    // 单个图片
	    map.put("singleImg", Pictures.ofLocal("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/resources/static/img/C1.jpg").size(120, 120).create());
	    // 多个图片
	    List<Map<String, PictureRenderData>> imgs = Lists.newArrayList();
	    for (int j = 0; j < 2; j++) {
	    	Map<String, PictureRenderData> picMap = Maps.newHashMap();
	    	picMap.put("picture", pictureRenderData);
	    	imgs.add(picMap);
		}
	    map.put("pictures", imgs);
	    PoiTlUtil.createWord(map, "demo2.docx", "", "测试Poi-Tl.docx", response);
	}
	
}
