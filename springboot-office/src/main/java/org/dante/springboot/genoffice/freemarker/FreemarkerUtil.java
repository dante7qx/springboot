package org.dante.springboot.genoffice.freemarker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.util.StringUtils;

import freemarker.core.XHTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Cleanup;

/**
 * 生成 HTML 文件
 *
 * @param dataMap 待填充数据
 * @param templateName 模板文件名称
 * @param templatePath 模板文件路径
 */
public class FreemarkerUtil {
	
	private static final String ROOT_DIR = "/templates/pdf/freemarker/";
	
	public static String generateHtml(Map<String, Object> dataMap, String templateName, String templatePath) throws IOException, TemplateException{
		// 创建配置实例
	    Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
	    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
	    configuration.setOutputFormat(XHTMLOutputFormat.INSTANCE);
	    configuration.setClassicCompatible(true);
	    // ftl模板文件
	    String basePackagePath = StringUtils.hasText(templatePath) ? ROOT_DIR.concat(templatePath) : ROOT_DIR;
	    configuration.setClassLoaderForTemplateLoading(FreemarkerUtil.class.getClassLoader(), basePackagePath);
        // 获取模板
        Template template = configuration.getTemplate(templateName, StandardCharsets.UTF_8.name());
        @Cleanup StringWriter stringWriter = new StringWriter();
        @Cleanup BufferedWriter writer = new BufferedWriter(stringWriter);
        // 生成Html
        template.process(dataMap, writer);
        writer.flush();
        return stringWriter.toString();
	}
	
}
