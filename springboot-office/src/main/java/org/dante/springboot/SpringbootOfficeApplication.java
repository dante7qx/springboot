package org.dante.springboot;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

/**
 * 解决使用Spring Boot、Multipartfile上传文件路径错误问题
 * http://blog.csdn.net/daniel7443/article/details/51620308
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOfficeApplication.class, args);
	}

	/**
	 * 奇怪问题：
	 * 
	 * @return
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/Users/dante/Documents/Project/spring/springboot/springboot-poi/src/main/resources/tmp");
		return factory.createMultipartConfig();
	}
}
