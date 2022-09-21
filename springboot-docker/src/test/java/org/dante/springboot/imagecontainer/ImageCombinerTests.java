package org.dante.springboot.imagecontainer;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import com.freewayso.image.combiner.ImageCombiner;
import com.freewayso.image.combiner.enums.OutputFormat;

import org.junit.jupiter.api.Test;

public class ImageCombinerTests {
	
	/**
	 * 问题：https://gitee.com/dromara/image-combiner/issues/I5IU4T
	 * 
	 * @throws Exception
	 */
	@Test
	public void simpleDemo() throws Exception {
		//合成器（指定背景图和输出格式，整个图片的宽高和相关计算依赖于背景图，所以背景图的大小是个基准）
	    ImageCombiner combiner = new ImageCombiner("http://dromara.gitee.io/image-combiner/media/sample2.png", OutputFormat.JPG);

	    //加图片元素
//	    combiner.addImageElement("http://xxx.com/image/product.png", 0, 300);

	    //加文本元素
	    combiner.addTextElement("周末大放送", "PingFangSC-Regular", 21, 100, 10).setColor(Color.white);

	    //执行图片合并
	    combiner.combine();

	    //可以获取流（并上传oss等）
	    InputStream is = combiner.getCombinedImageStream();
	    is.close();
	    //也可以保存到本地
	    combiner.save("/Users/dante/Documents/Project/java-world/springboot/springboot-docker/src/test/java/org/dante/springboot/imagecontainer/image.jpg");
	}
	
	/**
     * 显示所有可用字体
     */
    @Test
    public void showFonts() throws InterruptedException {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontName = e.getAvailableFontFamilyNames();
        for (int i = 0; i < fontName.length; i++) {
            System.out.println(fontName[i]);
        }
    }
	
}
