package org.dante.springboot.genoffice.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import freemarker.core.XMLOutputFormat;
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

	private static final String DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/resources/templates/xml/";
	
	
	
	public static void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName, HttpServletResponse response) throws IOException{
	    // 创建配置实例
	    Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
	    configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
	    configuration.setOutputFormat(XMLOutputFormat.INSTANCE);
	    configuration.setClassicCompatible(true);
	    // ftl模板文件
	    configuration.setDirectoryForTemplateLoading(StringUtils.hasText(filePath) ?  new File(DIR.concat(filePath)) : new File(DIR));
	    
	    Writer out = null;
	    try {
	        // 获取模板
	        Template template = configuration.getTemplate(templateName, StandardCharsets.UTF_8.name());
	        response.setHeader("Content-disposition",
	                "attachment;filename=" + URLEncoder.encode(fileName + ".doc", StandardCharsets.UTF_8.name()));
	        // 定义输出类型
	        response.setContentType("application/msword");
	        out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
	        // 生成文件
	        template.process(dataMap, out);
	        
	    } catch (Exception e){
	        e.printStackTrace();
	    } finally {
	    	out.flush();
	        out.close();
		}
	}
}
