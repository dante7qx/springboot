package org.dante.springboot.genoffice.thymeleaf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Component
public class ThymeleafWordUtil {

	private TemplateEngine templateEngine;
	
	public ThymeleafWordUtil(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setTemplateMode(TemplateMode.XML);
		resolver.setPrefix("templates/xml/"); // 模板文件的所在目录
		resolver.setSuffix(".xml"); // 模板文件后缀
		// 创建模板引擎
		this.templateEngine.setTemplateResolver(resolver);
	}

	public void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName,
			HttpServletResponse response) throws IOException {

		// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".doc", StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/msword");

		// 创建模版加载器
		
		
		String templatePath = StringUtils.hasText(filePath) ? filePath.concat(templateName) : templateName;
		Context context = new Context(Locale.getDefault(), dataMap);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			templateEngine.process(templatePath, context, out);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}

}
