package org.dante.springboot.word2pdf.spire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import org.dante.springboot.word2pdf.Consts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 冰蓝科技
 * 
 * http://www.e-iceblue.cn
 * 
 * @author dante
 *
 */
public class WordToPDF {
	
	public static void main(String[] args) throws IOException {
		if (!getLicense()) {
			return;
		}
        //实例化Document类的对象
        Document doc = null;
        FileOutputStream os = null;
        try {
        	doc = new Document(Consts.WORD_DIR.concat("合并文档.docx"));
        	os = new FileOutputStream(Consts.WORD_DIR.concat("spire/合并文档.pdf"));
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
        	os.close();
        }
        
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
