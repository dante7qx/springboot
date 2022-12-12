package org.dante.springboot.word2pdf.aspose;

import java.awt.Color;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.TextWatermark;
import com.spire.doc.documents.WatermarkLayout;

public class WordMerge {
	
	static String dir = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/word2pdf/";
	
	public static void main(String[] args) {
		
		// 合并文档
		merge();
		
		// 添加文本水印
//		addTextWatermark();
	}
	
	/**
	 * 合并文档
	 * 
	 */
	public static void merge() {
		//加载文档1到Document对象
		Document doc = new Document(dir.concat("系统建设方案.docx"));
		
		for (int i = 0; i < 5; i++) {
			//使用InsertTextFromFile方法将文档2合并到新文档
			doc.insertTextFromFile(dir.concat("清单.doc"), FileFormat.Docx_2013);
		}
		//保存文档
		doc.saveToFile(dir.concat("合并文档-1.docx"), FileFormat.Docx_2013);
//		WordToImg.restWord(dir.concat("合并文档.docx"));
	}
	
	/**
	 * 添加文本水印
	 * 
	 */
	public static void addTextWatermark() {
		//创建一个Document实例
        Document document = new Document();

        //加载示例 Word 文档
        document.loadFromFile(dir.concat("合并文档.docx"));

        //获取第一节
        Section section = document.getSections().get(0);

        //创建一个 TextWatermark 实例
        TextWatermark txtWatermark = new TextWatermark();

        //设置文本水印格式
        txtWatermark.setText("内部使用");
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);

        //将文本水印添加到示例文档
        section.getDocument().setWatermark(txtWatermark);

        //保存文件
        document.saveToFile("合并文档水印.docx", FileFormat.Docx);
        
	}
	
	/**
	 * 添加图片水印
	 * 
	 */
	public static void addImageWatermark() {
		
	}
	
}
