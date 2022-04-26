package org.dante.springboot.genoffice.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FreemarkerUtilTests {

	@Test
	public void generateHtml() {
		Map<String, Object> dataMap = Maps.newHashMap();
		try {
			List<String> titleList = Arrays.asList("属性1", "属性2", "属性3", "属性4", "属性5", "属性6", "属性7");
			dataMap.put("titleList", titleList);

			List<List<String>> dataList = new ArrayList<List<String>>();
			for (int i = 0; i < 5; i++) {
				dataList.add(Arrays.asList("数据1_" + i, "数据2_" + i, "数据3_数据3_数据3_数据3_数据3_数据3_数据3_数据3_数据3_" + i,
						"数据4_" + i, "数据5_" + i, "数据6_" + i, "数据7_" + i));
			}
			dataMap.put("dataList", dataList);
			String generateHtml = FreemarkerUtil.generateHtml(dataMap, "hello.html", null);
			log.info("{}", generateHtml);
		} catch (IOException | TemplateException e) {
			log.error(e.getMessage(), e);
		}
	}

}
