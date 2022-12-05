package org.dante.springboot.genoffice.poitl;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Maps;
import org.dante.springboot.genoffice.poitl.plugin.TreeTablePolicy;
import org.dante.springboot.genoffice.vo.NormColVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

import cn.hutool.core.collection.CollUtil;

public class TreeTablePolicyTests {

	private List<NormColVO> norms = CollUtil.newArrayList();

	@BeforeEach
	public void init() {
		norms.add(new NormColVO("绩效指标", "产出指标", "数量指标", "种植面积", "》 亩"));
		norms.add(new NormColVO("绩效指标", "产出指标", "数量指标", "种植面积", "》 亩"));
		norms.add(new NormColVO("绩效指标", "产出指标", "数量指标", "其他种植面积", "》 亩"));
		norms.add(new NormColVO("绩效指标", "产出指标", "成本指标", "亩均成本", "》 元/亩"));
		norms.add(new NormColVO("绩效指标", "产出指标", "成本指标", "亩均成本", "》 元/亩"));
		norms.add(new NormColVO("绩效指标", "效益指标", "经济效益指标", "亩产值", "》 元"));
		norms.add(new NormColVO("绩效指标", "效益指标", "经济效益指标", "亩产值", "》 元"));
		norms.add(new NormColVO("绩效指标", "效益指标", "社会效益指标", "就业人数", "》 户"));
		norms.add(new NormColVO("绩效指标", "效益指标", "社会效益指标", "总收入", "》 万元"));
		norms.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "受益人口", "》  %"));
		norms.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "农业经营", "》  %"));
		norms.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "科技、技术、农业", "》  %"));
	}
	
	@Test
	public void treeTableProlicy() throws Exception {
		String templateDir = PoiTlUtil.class.getClassLoader().getResource("templates/word/poitl/").getPath();
		Configure config = Configure.builder()
				.bind("normTable", new TreeTablePolicy())
				.build();
		Map<String, Object> map = Maps.newHashMap("normTable", norms);
        XWPFTemplate template = XWPFTemplate.compile(templateDir + "dynaTable.docx", config).render(map);
        template.writeToFile("target/指标树.docx");
	}
	
}
