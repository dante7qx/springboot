package org.dante.springboot.word2pdf.aspose;

import java.awt.Color;
import java.io.OutputStream;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.HeaderFooter;
import com.spire.doc.Section;
import com.spire.doc.TextWatermark;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ShapeLineStyle;
import com.spire.doc.documents.ShapeType;
import com.spire.doc.documents.WatermarkLayout;
import com.spire.doc.fields.ShapeObject;

/**
 * Word 添加水印工具类
 * 
 * @author dante
 *
 */
public class WordWaterMarkUtil {
	
	/**
	 * 添加文本水印
	 * 
	 * @param sourcePath 源文件
	 * @param targetPath 目标文件
	 * @param waterText 水印文字
	 */
	public static void addTextWatermark(String sourcePath, String targetPath, String waterText) {
        Document doc = addSingleTextWatermark(sourcePath, waterText);
        doc.saveToFile(targetPath, FileFormat.Docx);
        doc.close();
	}
	
	/**
	 * 添加文本水印
	 * 
	 * @param sourcePath 源文件
	 * @param outputStream 输出流
	 * @param waterText	水印文字
	 */
	public static void addTextWatermarkToStream(String sourcePath, OutputStream outputStream, String waterText) {
        Document doc = addSingleTextWatermark(sourcePath, waterText);
        doc.saveToStream(outputStream, FileFormat.Docx);
        doc.close();
	}
	
	private static Document addSingleTextWatermark(String sourcePath, String waterText) {
		//创建一个Document实例
        Document doc = new Document();
        //加载示例 Word 文档
        doc.loadFromFile(sourcePath);
        //获取第一节
        Section section = doc.getSections().get(0);
        //创建一个 TextWatermark 实例
        TextWatermark txtWatermark = new TextWatermark();
        //设置文本水印格式
        txtWatermark.setText(waterText);
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);
        //将文本水印添加到示例文档
        section.getDocument().setWatermark(txtWatermark);
        return doc;
	}
	
	/**
	 * 添加多行文本水印
	 * 
	 * @param sourcePath 源文件
	 * @param targetPath 目标文件
	 * @param waterText 水印文字
	 */
	public static void addMutiTextWatermark(String sourcePath, String targetPath, String waterText) {
		Document doc = addMutiTextWatermark(sourcePath, waterText);
        doc.saveToFile(targetPath, FileFormat.Docx);
        doc.close();
	}
	
	/**
	 * 添加多行文本水印
	 * 
	 * @param sourcePath 源文件
	 * @param outputStream 输出流
	 * @param waterText 水印文字
	 */
	public static void addMutiTextWatermarkToStream(String sourcePath, OutputStream outputStream, String waterText) {
		Document doc = addMutiTextWatermark(sourcePath, waterText);
        doc.saveToStream(outputStream, FileFormat.Docx);
        doc.close();
	}
	
	private static Document addMutiTextWatermark(String sourcePath, String waterText) {
		Document doc = new Document();
        doc.loadFromFile(sourcePath);
        //添加艺术字并设置大小
        ShapeObject shape = new ShapeObject(doc, ShapeType.Text_Plain_Text);
        shape.setWidth(waterText.length() * 27);
        shape.setHeight(20);
        //设置艺术字文本内容、位置及样式
        shape.setVerticalPosition(30);
        shape.setHorizontalPosition(20);
        shape.setRotation(315);
        shape.getWordArt().setFontFamily("宋体");
        shape.getWordArt().setText(waterText);
        shape.setFillColor(Color.red);
        shape.setLineStyle(ShapeLineStyle.Single);
        shape.setStrokeColor(new Color(192, 192, 192, 255));
        shape.setStrokeWeight(1);

        Section section;
        HeaderFooter header;
        for (int n = 0; n < doc.getSections().getCount(); n++) {
            section = doc.getSections().get(n);
            //获取section的页眉
            header = section.getHeadersFooters().getHeader();
            Paragraph paragraph;

            if (header.getParagraphs().getCount() > 0) {
                //如果页眉有段落，取它第一个段落
                paragraph = header.getParagraphs().get(0);
            } else {
                //否则新增加一个段落到页眉
                paragraph = header.addParagraph();
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    //复制艺术字并设置多行多列位置
                    shape = (ShapeObject) shape.deepClone();
                    shape.setVerticalPosition(120 + 150 * i);
                    shape.setHorizontalPosition(160 * j);
                    paragraph.getChildObjects().add(shape);
                }
            }
        }
        return doc;
	}
	
	public static void main(String[] args) {
		String dir = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/word2pdf/";
		
//		WordWaterMarkUtil.addTextWatermark(dir.concat("系统建设方案.docx"), "水印1.docx", "内部保密资料");
		
		WordWaterMarkUtil.addMutiTextWatermark(dir.concat("测试.docx"), "水印2.docx", "内部保密封");
		
	}
	
}
