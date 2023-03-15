package org.dante.springboot.poi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DeleteBlankPages {
	
	static final String DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/poi/";
	
	public static void main(String[] args) throws Exception {
        // 打开Word文档
        File file = new File(DIR + "11.docx");
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(file));

        // 遍历每个页码
        for (int i = 0; i < doc.getParagraphs().size(); i++) {
            XWPFParagraph para = doc.getParagraphs().get(i);
            
            
            
            // 如果该页中所有段落的文本内容都是空白字符，则删除该页
//            if (isPageBlank(doc, i)) {
//                doc.removeBodyElement(i);
//                i--;
//            }
        }

        // 保存修改后的文档
        doc.write(new FileOutputStream(DIR + "22.docx"));
        doc.close();
    }

    private static boolean isPageBlank(XWPFDocument doc, int pageNum) {
        int paraNum = doc.getParagraphs().size();
        int nextParaNum = (pageNum + 1 < paraNum) ? pageNum + 1 : paraNum;
        for (int i = pageNum; i < nextParaNum; i++) {
            XWPFParagraph para = doc.getParagraphs().get(i);
            if (!para.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
	
}
