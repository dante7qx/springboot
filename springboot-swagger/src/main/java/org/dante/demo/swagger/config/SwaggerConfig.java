package org.dante.demo.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()  // 选择那些路径和api会生成document
//	                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
//	                .paths(PathSelectors.any()) // 对所有路径进行监控
	                .apis(RequestHandlerSelectors.basePackage("org.dante.demo.swagger.controller"))
	                .paths(PathSelectors.any())
	                .build()
	                .apiInfo(apiInfo());
	    }
	 
	 private ApiInfo apiInfo() {
		 Contact contact =  new Contact("但丁", "", "ch.sun@hnair.com");
		    ApiInfo apiInfo = new ApiInfo(
		      "DANTE REST API",
		      "使用Swagger进行 REST API 描述.",
		      "API 版本",
		      "Terms of service",
		      contact,
		      "License of API",
		      "API license URL");
		    return apiInfo;
		}
}
