package org.dante.springboot.genoffice.freemarker;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成 word 文件
 *
 * @param dataMap 待填充数据
 * @param templateName 模板文件名称
 * @param filePath 模板文件路径
 * @param fileName 生成的 word 文件名称
 * @param response 响应流
 */
public class FreemarkerWordUtil {
	public static void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName, HttpServletResponse response){
	    
	    // 创建配置实例
	    Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
	    // 设置编码
	    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
	    // ftl模板文件
	    configuration.setClassForTemplateLoading(FreemarkerWordUtil.class, filePath);

	    try {
	    
	        // 获取模板
	        Template template = configuration.getTemplate(templateName);
	        response.setHeader("Content-disposition",
	                "attachment;filename=" + URLEncoder.encode(fileName + ".doc", StandardCharsets.UTF_8.name()));
	        // 定义输出类型
	        response.setContentType("application/msword");
	        Writer out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
	        // 生成文件
	        template.process(dataMap, out);

	        out.flush();
	        out.close();
	    } catch (Exception e){
	    
	        e.printStackTrace();
	    }
	}
}
