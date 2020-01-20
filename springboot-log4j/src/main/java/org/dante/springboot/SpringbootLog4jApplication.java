package org.dante.springboot;

import org.dante.springboot.prop.SpiritProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * log4j 配置详解
 * 
 * https://blog.csdn.net/satomiyo/article/details/100040235
 * https://blog.csdn.net/niuch1029291561/article/details/80938095
 * 
 * 使用外置Tomcat部署，404问题
 * 1. 在pom注入依赖 
 * <dependency>
	   <groupId>org.springframework.boot</groupId>
	   <artifactId>spring-boot-starter-tomcat</artifactId>
	   <scope>provided</scope>
  </dependency>
 *
 *2. 在启动类重写 configure 方法
 * 
 * @author dante
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(SpiritProperties.class)
public class SpringbootLog4jApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLog4jApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootLog4jApplication.class);
	}
}
