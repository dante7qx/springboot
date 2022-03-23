package org.dante.springboot.genoffice.poitl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;

/**
 * 参考文档：http://deepoove.com/poi-tl
 * 
 * @author dante
 *
 */
public class PoiTLUtil {

	private static final String TEMPLATE_DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/genoffice/poitl/";

	public static void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName,
			HttpServletResponse response) throws FileNotFoundException, IOException {

		// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");

		String templatePath = StringUtils.hasText(filePath) ? TEMPLATE_DIR.concat(filePath).concat(templateName) : TEMPLATE_DIR.concat(templateName);
		XWPFTemplate template = XWPFTemplate.compile(templatePath).render(dataMap);
		
		OutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			out = response.getOutputStream();
			bos = new BufferedOutputStream(out);
			template.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bos.flush();
			out.flush();
			PoitlIOUtils.closeQuietlyMulti(template, bos, out);
		}
		

		
		
	}

}
