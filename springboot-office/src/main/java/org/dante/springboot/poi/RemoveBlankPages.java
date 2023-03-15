package org.dante.springboot.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class RemoveBlankPages {
	
	static final String DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/poi/";
	
	public static void main(String[] args) throws Exception {
	      FileInputStream fis = new FileInputStream(DIR + "input.docx");
	      XWPFDocument doc = new XWPFDocument(fis);
	      
	      // 遍历文档中的每一页
	      for (int i = doc.getParagraphs().size() - 1; i >= 0; i--) {
	         XWPFParagraph para = doc.getParagraphs().get(i);
	         if (para.getRuns().isEmpty()) {
	            // 如果该页中没有段落，则该页为空白页，删除该页
	            doc.removeBodyElement(i);
	         }
	      }
	      
	      FileOutputStream fos = new FileOutputStream(DIR + "output.docx");
	      doc.write(fos);
	      fos.close();
	      fis.close();
	   }
	
}
