package org.dante.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域访问方式2
 * 
 * @author dante
 *
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:8080")
        .allowedMethods("GET", "POST")
        .allowCredentials(false)	// 不发送Cookie和HTTP认证信息
        .maxAge(3600);;
	}
	
}
