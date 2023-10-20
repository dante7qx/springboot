package org.dante.springboot.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import fmath.conversion.ConvertFromLatexToMathML;
import fmath.conversion.ConvertFromMathMLToLatex;
import fmath.conversion.ConvertFromWordToMathML;
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
		String mathML = ConvertFromWordToMathML.getMathMLFromDocFile("/Users/dante/Desktop/332.docx");
//		Console.log(mathML);
//		Console.log(mathML.replaceAll("\n\\s*", ""));
		String latex = mathMLTolaTeX(mathML);
		Console.log(latex);
		String result = formatLatex(latex);
		Console.log(result);
	}
	
	public static void main1(String[] args) {
//		String mathML="<math>  \n" +
//                "      <mrow>  \n" +
//                "        <msup><mi>a</mi><mn>2</mn></msup>  \n" +
//                "        <mo>+</mo>  \n" +
//                "        <msup><mi>b</mi><mn>2</mn></msup>  \n" +
//                "        <mo>=</mo>  \n" +
//                "        <msup><mi>c</mi><mn>2</mn></msup>  \n" +
//                "      </mrow>  \n" +
//                "    </math>  ";
		String mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">  <msub>    <mi>S</mi>    <mrow>      <mi>n</mi>    </mrow>  </msub>  <mo>=</mo>  <mi>n</mi>  <msub>    <mi>a</mi>    <mrow>      <mn>1</mn>    </mrow>  </msub>  <mo>+</mo>  <mfrac>    <mrow>      <mi>n</mi>      <mrow data-mjx-texclass=\"INNER\">        <mo data-mjx-texclass=\"OPEN\">(</mo>        <mi>n</mi>        <mo>&#x2212;</mo>        <mn>1</mn>        <mo data-mjx-texclass=\"CLOSE\">)</mo>      </mrow>    </mrow>    <mrow>      <mn>2</mn>    </mrow>  </mfrac>  <mi>d</mi></math>";
		String OOXml = mathML2OOXml(mathML);
		String laTeX = mathMLTolaTeX(mathML);
		String html = mathMLToHtml(mathML);
		Console.log(OOXml);
		Console.log("===========================");
		Console.log(laTeX);
		Console.log(formatLatex(laTeX));
		Console.log("===========================");
		Console.log(laTeXToMathML(laTeX));
		Console.log("===========================");
		Console.log(html);
		
	}
	
	private static String formatLatex(String latex) {
		return latex
				.replaceAll("\\^(\\\\pm\\s+\\w+\\s+%)", "^{$1}")
				.replaceAll("\\%", "\\\\%")
				.replaceAll("\\\\textdegree", "{°}")
				.replaceAll("\\\\minus", "-")
				.replaceAll("f\\^\\^", "\\\\hat f")
				.replaceAll("\\\\pi(\\w?)", "\\\\pi $1 ")
				.replaceAll("\\\\xi(\\w?)", "\\\\xi $1 ")
				;
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
