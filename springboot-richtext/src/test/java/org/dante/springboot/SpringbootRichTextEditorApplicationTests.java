package org.dante.springboot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class SpringbootRichTextEditorApplicationTests {

	@Test
	public void testMd2File() throws Exception {
		File file = new File("/Users/dante/Documents/Project/java-world/springboot/springboot-richtext/tmp.md");
		URL url = new URL(
				"http://localhost:8700/dante/dante-gitlab-cms/-/raw/master/%E4%BA%A7%E5%93%81%E8%AF%B4%E6%98%8E/%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B.md?inline=false");
		FileUtils.copyURLToFile(url, file);

		String mdContent = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		FileUtils.forceDelete(file);
		System.out.println(mdContent);
	}

	@Test
	public void testURL2Str() throws IOException {
		String url = "https://raw.githubusercontent.com/AlibabaCloudDocs/ecs/master/intl.zh-CN/%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/%E4%BB%80%E4%B9%88%E6%98%AF%E4%BA%91%E6%9C%8D%E5%8A%A1%E5%99%A8ECS.md";
		URL urlObj = new URL(url);
		BufferedReader br = new BufferedReader(new InputStreamReader(urlObj.openStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}
		System.out.println(sb);
		br.close();
	}
}
