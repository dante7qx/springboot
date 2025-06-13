package org.dante.springboot.pdf2word.spire;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.aspose.words.License;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

public class Pdf2Word {
	
	public static void main(String[] args) {
		if (!getLicense()) {
			return;
		}
		 //创建PdfDocument对象
        PdfDocument doc = new PdfDocument();

        //加载PDF文件
        doc.loadFromFile("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/pdf2word/医疗质量指标集汇总4.0版.pdf");

        //将PDF转换为Doc文档并保存到指定的路径
        doc.saveToFile("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/pdf2word/ToDoc.doc", FileFormat.DOC);

        //将PDF转换为Docx文档并保存到指定的路径
        doc.saveToFile("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/pdf2word/ToDocx.docx", FileFormat.DOCX);
        doc.close();
	}
	
	public static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            //InputStream is = Word2PdfAsposeUtil.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
