package org.dante.springboot.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class DBGenerator {

	private static final String OUTPUT = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		List<String> tables = new ArrayList<>();
        tables.add("t_userinfo");
		
		
		FastAutoGenerator.create("jdbc:postgresql://localhost:5432/test?useUnicode=true&characterEncoding=UTF-8", "postgres", "iamdante")
	    .globalConfig(builder -> {
	        builder.author("dante") // 设置作者
//	            .enableSwagger() // 开启 swagger 模式
	            .commentDate("yyyy-MM-dd")
//	            .fileOverride() // 覆盖已生成文件
	            .disableOpenDir() // 禁止打开输出目录
	            .outputDir(OUTPUT); // 指定输出目录
	    })
	    .packageConfig(builder -> {
	        builder.parent("org.dante") // 设置父包名
	            .moduleName("springboot") // 设置父包模块名
	            .entity("po")
                .service("service")
                .serviceImpl("service.impl")
                .controller("controller")
                .mapper("dao")
                .xml("mapper")
                .pathInfo(Collections.singletonMap(OutputFile.xml,System.getProperty("user.dir")+"/src/main/resources/mybatis/mapper")); // 设置mapperXml生成路径
	    })
	    .strategyConfig(builder -> {
            builder.addInclude(tables)
                    .addTablePrefix("t_")
                    .serviceBuilder()
                    .formatServiceFileName("%sService")
                    .formatServiceImplFileName("%sServiceImpl")
                    .entityBuilder()
                    .enableLombok()
                    .logicDeleteColumnName("deleted")
                    .enableTableFieldAnnotation()
                    .controllerBuilder()
                    // 映射路径使用连字符格式，而不是驼峰
                    .enableHyphenStyle()
                    .formatFileName("%sController")
                    .enableRestStyle()
                    .mapperBuilder()
                    //生成通用的resultMap
                    .enableBaseResultMap()  
                    .superClass(BaseMapper.class)
                    .formatMapperFileName("%sMapper")
                    .enableMapperAnnotation()
                    .formatXmlFileName("%sMapper");
        })
	    
	    /*
	    .templateConfig(new Consumer<TemplateConfig.Builder>() {
            @Override
            public void accept(TemplateConfig.Builder builder) {
                // 实体类使用我们自定义模板
                builder.entity("templates/myentity.java");
            }
        })
        */
	    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
	    .execute();
	}
	
}
