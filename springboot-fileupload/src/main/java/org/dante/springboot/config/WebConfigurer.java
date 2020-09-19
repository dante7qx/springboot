package org.dante.springboot.config;

import org.dante.springboot.interceptor.FileUploadInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new FileUploadInterceptor()).addPathPatterns("/**")
			.excludePathPatterns("/favicon.ico");
	}
	
}
