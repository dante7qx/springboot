package org.dante.springboot.genoffice.poi;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class MergeToOneLine {

	public static void main(String[] args) throws IOException {
		// 创建一个新的空白Word文档
		XWPFDocument document = new XWPFDocument();

		// 创建一个段落
		XWPFParagraph paragraph = document.createParagraph();

		// 创建一个文本运行
		XWPFRun run = paragraph.createRun();

		// 设置正常文本
		run.setText("H");

		// 添加上标
		run.setText("\u00B2"); // Unicode编码2代表上标2
		run.setSubscript(VerticalAlign.SUPERSCRIPT);

		// 添加下标
		run.setText("\u2085"); // Unicode编码5代表下标5
		run.setSubscript(VerticalAlign.SUBSCRIPT);
		

		// 保存Word文档
		try (FileOutputStream out = new FileOutputStream(
				"/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/test/java/org/dante/springboot/genoffice/poi/output.docx")) {
			document.write(out);
			System.out.println("Word文档导出成功！");
		} catch (IOException e) {
			System.out.println("导出Word文档时出现错误：" + e.getMessage());
		} finally {
			document.close();
		}
	}

}
