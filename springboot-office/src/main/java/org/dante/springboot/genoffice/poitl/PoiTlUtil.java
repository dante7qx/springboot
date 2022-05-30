package org.dante.springboot.genoffice.poitl;

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
public class PoiTlUtil {


	public static void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		String templateDir = PoiTlUtil.class.getClassLoader().getResource("templates/word/poitl/").getPath();
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");

		String templatePath = StringUtils.hasText(filePath) ? templateDir.concat(filePath).concat(templateName) : templateDir.concat(templateName);
		XWPFTemplate template = XWPFTemplate.compile(templatePath).render(dataMap);
		
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			template.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			PoitlIOUtils.closeQuietlyMulti(template, out);
		}
	}
	
}
