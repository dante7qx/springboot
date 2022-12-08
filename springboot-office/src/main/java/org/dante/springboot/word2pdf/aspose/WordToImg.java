package org.dante.springboot.word2pdf.aspose;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.ImageType;


public class WordToImg {
	
	public static void changeDocToImg(InputStream inputStream, String imgName) {
		try {
			Document doc = new Document();
			// 加载文件 第二个参数 FileFormat.Auto 会自动去分别上传文件的 docx、doc类型
			doc.loadFromStream(inputStream, FileFormat.Auto);
			//上传文档页数，也是最后要生成的图片数
            Integer pageCount = doc.getPageCount();
            // 参数第一个和第三个都写死 第二个参数就是生成图片数
            BufferedImage[] image = doc.saveToImages(0, pageCount, ImageType.Bitmap);
            // 循环，输出图片保存到本地
            for (int i = 0; i < image.length; i++) {
                File f = new File("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/word2pdf/aspose/" + imgName + "_" + (i + 1) + ".png");
                ImageIO.write(image[i], "PNG", f);
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		WordToImg.changeDocToImg(new FileInputStream("/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/word2pdf/测试.docx"), "Word2Image");
	}
	
}
