package org.dante.springboot.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import fmath.conversion.ConvertFromLatexToMathML;
import fmath.conversion.ConvertFromMathMLToLatex;
import fmath.conversion.c.a;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * LaTeX 公式转换工具类（LaTeX - MathML - OOXml）
 * 
 * @author dante
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LaTeXUtil {

	public static String mathML2OOXml(String mathml) {
		String formatMathml = ReUtil.replaceAll(mathml, "<math\\s[^>]*>", "<math>");
		String openXML = a.a(formatMathml);
		String header = "<m:oMathPara xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\">";
		openXML = header + openXML + "</m:oMathPara>";
		return openXML;
	}
	
	public static String mathMLTolaTeX(String mathML) {
		return ConvertFromMathMLToLatex.convertToLatex(mathML);
	}
	
	public static String laTeXToMathML(String laTeX) {
		return ConvertFromLatexToMathML.convertToMathML(laTeX);
	}
	
	public static String mathMLToHtml(String mathML) {
	        return ReUtil.replaceAll(mathML, "(>\\s+<)", "><");
	}

	public static void main(String[] args) {
//		String mathML="<math>  \n" +
//                "      <mrow>  \n" +
//                "        <msup><mi>a</mi><mn>2</mn></msup>  \n" +
//                "        <mo>+</mo>  \n" +
//                "        <msup><mi>b</mi><mn>2</mn></msup>  \n" +
//                "        <mo>=</mo>  \n" +
//                "        <msup><mi>c</mi><mn>2</mn></msup>  \n" +
//                "      </mrow>  \n" +
//                "    </math>  ";
		String mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">  <mfrac>    <mn>1</mn>    <mrow>      <mn>4</mn>      <msup>        <mi>n</mi>        <mrow>          <mn>2</mn>        </mrow>      </msup>      <mo>&#x2212;</mo>      <mn>1</mn>    </mrow>  </mfrac>  <mo>=</mo>  <mfrac>    <mn>1</mn>    <mn>2</mn>  </mfrac>  <mrow data-mjx-texclass=\"INNER\">    <mo data-mjx-texclass=\"OPEN\">(</mo>    <mfrac>      <mn>1</mn>      <mrow>        <mn>2</mn>        <mi>n</mi>        <mo>&#x2212;</mo>        <mn>1</mn>      </mrow>    </mfrac>    <mo>&#x2212;</mo>    <mfrac>      <mn>1</mn>      <mrow>        <mn>2</mn>        <mi>n</mi>        <mo>+</mo>        <mn>1</mn>      </mrow>    </mfrac>    <mo data-mjx-texclass=\"CLOSE\">)</mo>  </mrow></math>";
		String OOXml = mathML2OOXml(mathML);
		String laTeX = mathMLTolaTeX(mathML);
		String html = mathMLToHtml(mathML);
		Console.log(OOXml);
		Console.log("===========================");
		Console.log(laTeX);
		Console.log("===========================");
		Console.log(laTeXToMathML(laTeX));
		Console.log("===========================");
		Console.log(html);
	}
}

/**
　睿阳RSP框架加入数学公式插件
	
  1. 添加依赖（设置公司的私服）
  <dependency>
	  <groupId>com.risun</groupId>
	  <artifactId>latex-mathml-ooxml</artifactId>
	  <version>1.0.0</version>
  </dependency>
  <dependency>
	  <groupId>org.jdom</groupId>
	  <artifactId>jdom2</artifactId>
	  <version>2.0.6.1</version>
  </dependency>

  2.添加导出插件
  Configure.builder().addPlugin('%', new LaTeXPolicy())
  
  3. 传入参数是MathML格式
  map.put("fn", "数学公式 MathML 格式");
  
  4. Word模板
  {{%fn}}
 */
