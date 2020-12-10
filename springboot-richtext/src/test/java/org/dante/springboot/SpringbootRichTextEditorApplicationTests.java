package org.dante.springboot;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class SpringbootRichTextEditorApplicationTests {

	@Test
	public void test() throws Exception {
		File file = new File("/Users/dante/Documents/Project/java-world/springboot/springboot-richtext/tmp.md");
		URL url = new URL("http://localhost:8700/dante/dante-gitlab-cms/-/raw/master/%E4%BA%A7%E5%93%81%E8%AF%B4%E6%98%8E/%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B.md?inline=false");
		FileUtils.copyURLToFile(url, file);
		
		String mdContent = FileUtils.readFileToString(file);
		FileUtils.forceDelete(file);
		System.out.println(mdContent);
		
	}
}
