package org.dante.springboot.genoffice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.genoffice.poitl.PoiTlUtil;
import org.dante.springboot.genoffice.vo.NormColVO;
import org.dante.springboot.util.LaTeXUtil;
import org.dante.springboot.util.NanoIdUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;

@RestController
@RequestMapping("/word")
public class WordController {
	
	@RequestMapping("/poitl/demo")
	public void poitlDome(HttpServletResponse response) throws IOException {
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
//	    PictureRenderData pictureRenderData = Pictures.ofLocal("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/resources/static/img/C1.jpg").size(120, 120).create();
	    // 单个图片
//	    map.put("singleImg", Pictures.ofLocal("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/resources/static/img/C1.jpg").size(120, 120).create());
	    /*
	    // 多个图片
	    List<Map<String, PictureRenderData>> imgs = Lists.newArrayList();
	    for (int j = 0; j < 2; j++) {
	    	Map<String, PictureRenderData> picMap = Maps.newHashMap();
	    	picMap.put("picture", pictureRenderData);
	    	imgs.add(picMap);
		}
	    map.put("pictures", imgs);
	    */
	    
	    // 数学公式OpenXML (OOXml)
	    this.addMathOOXml(map);
	    

	    PoiTlUtil.createWord(map, "demo2.docx", "", "测试Poi-Tl.docx", response);
	}
	
	
	@RequestMapping("/poitl/tree_table")
	public void poitlTreeTable(HttpServletResponse response) throws IOException {
		final Map<String, Object> map = Maps.newHashMap();
		map.put("normTable", buildData());
		PoiTlUtil.createWord(map, "dynaTable.docx", "", "指标树.docx", response);
	}
	
	private static List<NormColVO> buildData() {
		List<NormColVO> lists = CollUtil.newArrayList();
		lists.add(new NormColVO("绩效指标", "产出指标", "数量指标", "种植面积", "》 亩"));
		lists.add(new NormColVO("绩效指标", "产出指标", "数量指标", "种植面积", "》 亩"));
		lists.add(new NormColVO("绩效指标", "产出指标", "数量指标", "其他种植面积", "》 亩"));
		
		lists.add(new NormColVO("绩效指标", "产出指标", "成本指标", "亩均成本", "》 元/亩"));
		lists.add(new NormColVO("绩效指标", "产出指标", "成本指标", "亩均成本", "》 元/亩"));
		
		lists.add(new NormColVO("绩效指标", "效益指标", "经济效益指标", "亩产值", "》 元"));
		lists.add(new NormColVO("绩效指标", "效益指标", "经济效益指标", "亩产值", "》 元"));
		lists.add(new NormColVO("绩效指标", "效益指标", "社会效益指标", "就业人数", "》 户"));
		lists.add(new NormColVO("绩效指标", "效益指标", "社会效益指标", "总收入", "》 万元"));
		
		
		lists.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "受益人口", "》  %"));
		lists.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "农业经营", "》  %"));
		lists.add(new NormColVO("绩效指标", "满意度指标", "服务对象满意度指标", "科技、技术、农业", "》  %"));
		
		return lists;
	}
	
	private void addMathOOXml(Map<String, Object> map) {
		map.put("fnOne", "<m:oMath xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\">" +
                "<m:rad>" +
                "<m:radPr>" +
                "<m:degHide m:val=\"on\"/>" +
                "</m:radPr>" +
                "<m:e>" +
                "<m:r>" +
                "<m:t>(a+b)^2 = a^2 + 2ab + b^2</m:t>" +
                "</m:r>" +
                "</m:e>" +
                "</m:rad>" +
                "</m:oMath>");
	    map.put("fnTwo", "<m:oMath xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\">"
        		+ "<m:f><m:fPr><m:ctrlPr></m:ctrlPr></m:fPr><m:num><m:r><m:rPr><m:sty m:val=\"p\"/></m:rPr><m:t>d</m:t></m:r></m:num><m:den><m:r><m:rPr><m:sty m:val=\"p\"/></m:rPr><m:t>d</m:t></m:r><m:r><m:t>x</m:t></m:r></m:den></m:f><m:sSup><m:sSupPr><m:ctrlPr></m:ctrlPr></m:sSupPr><m:e><m:r><m:t>x</m:t></m:r></m:e><m:sup><m:r><m:t>n</m:t></m:r></m:sup></m:sSup><m:r><m:t>=n</m:t></m:r><m:sSup><m:sSupPr><m:ctrlPr></m:ctrlPr></m:sSupPr><m:e><m:r><m:t>x</m:t></m:r></m:e><m:sup><m:r><m:t>n-1</m:t></m:r></m:sup></m:sSup>"
        		+ "</m:oMath>");
	    
	    map.put("fnThree", "<m:oMathPara xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\"><m:oMath>\n"
	    		+ "    <m:f>\n"
	    		+ "        <m:fPr>\n"
	    		+ "            <m:ctrlPr>\n"
	    		+ "            </m:ctrlPr>\n"
	    		+ "        </m:fPr>\n"
	    		+ "        <m:num>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>d</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:num>\n"
	    		+ "        <m:den>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>d</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>x</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:den>\n"
	    		+ "    </m:f>\n"
	    		+ "    <m:sSup>\n"
	    		+ "        <m:sSupPr>\n"
	    		+ "            <m:ctrlPr>\n"
	    		+ "            </m:ctrlPr>\n"
	    		+ "        </m:sSupPr>\n"
	    		+ "        <m:e>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>x</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:e>\n"
	    		+ "        <m:sup>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>n</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:sup>\n"
	    		+ "    </m:sSup>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>=</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>n</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:sSup>\n"
	    		+ "        <m:sSupPr>\n"
	    		+ "            <m:ctrlPr>\n"
	    		+ "            </m:ctrlPr>\n"
	    		+ "        </m:sSupPr>\n"
	    		+ "        <m:e>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>x</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:e>\n"
	    		+ "        <m:sup>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>n</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>−</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>1</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:sup>\n"
	    		+ "    </m:sSup>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t></m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:eqArr>\n"
	    		+ "        <m:eqArrPr>\n"
	    		+ "            <m:ctrlPr>\n"
	    		+ "            </m:ctrlPr>\n"
	    		+ "        </m:eqArrPr>\n"
	    		+ "        <m:e>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>a</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>⊥</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>α</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:e>\n"
	    		+ "        <m:e>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>b</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>⊥</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "            <m:r>\n"
	    		+ "                <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "                <m:t>α</m:t>\n"
	    		+ "            </m:r>\n"
	    		+ "        </m:e>\n"
	    		+ "    </m:eqArr>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>}</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>⇒</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>a</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>∥</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "    <m:r>\n"
	    		+ "        <w:rPr><w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\"/></w:rPr>\n"
	    		+ "        <m:t>b</m:t>\n"
	    		+ "    </m:r>\n"
	    		+ "</m:oMath>\n"
	    		+ "</m:oMathPara>");
	    
	    
	    String mathml4 = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">  <mfrac>    <mn>1</mn>    <mrow>      <mn>4</mn>      <msup>        <mi>n</mi>        <mrow>          <mn>2</mn>        </mrow>      </msup>      <mo>&#x2212;</mo>      <mn>1</mn>    </mrow>  </mfrac>  <mo>=</mo>  <mfrac>    <mn>1</mn>    <mn>2</mn>  </mfrac>  <mrow data-mjx-texclass=\"INNER\">    <mo data-mjx-texclass=\"OPEN\">(</mo>    <mfrac>      <mn>1</mn>      <mrow>        <mn>2</mn>        <mi>n</mi>        <mo>&#x2212;</mo>        <mn>1</mn>      </mrow>    </mfrac>    <mo>&#x2212;</mo>    <mfrac>      <mn>1</mn>      <mrow>        <mn>2</mn>        <mi>n</mi>        <mo>+</mo>        <mn>1</mn>      </mrow>    </mfrac>    <mo data-mjx-texclass=\"CLOSE\">)</mo>  </mrow></math>";
	    map.put("fnFour", LaTeXUtil.mathML2OOXml(mathml4));
	    
	    String laTeX = "\\text{cos}^{2}\\frac{\\alpha}{2}=\\frac{1+ \\text{cos}\\alpha}{2} ";
	    String five1 = LaTeXUtil.mathML2OOXml(LaTeXUtil.laTeXToMathML(laTeX));
	    String five2 = LaTeXUtil.mathML2OOXml("<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">  <msup>    <mtext>cos</mtext>    <mrow>      <mn>2</mn>    </mrow>  </msup>  <mfrac>    <mi>&#x3B1;</mi>    <mn>2</mn>  </mfrac>  <mo>=</mo>  <mfrac>    <mrow>      <mn>1</mn>      <mo>+</mo>      <mtext>cos</mtext>      <mi>&#x3B1;</mi>    </mrow>    <mn>2</mn>  </mfrac></math>");
	    map.put("fnFive1", five1);
	    map.put("fnFive2", five2); 
	}
	
	/* 
	参考： https://www.cnblogs.com/hooly/p/8810865.html
	private Stream<NormVO> findChild(NormVO norm) {
		return norms.stream()
			.filter(d -> Objects.equals(norm.getId(), d.getPid()))
			.flatMap(d -> Stream.concat(Stream.of(d), findChild(d)));
	}
	*/
	
}
