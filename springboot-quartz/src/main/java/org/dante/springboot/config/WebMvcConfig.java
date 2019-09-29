package org.dante.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 页面配置
 * 
 * @author dante
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/scheduler").setViewName("schedulerlist");
		registry.addViewController("/editscheduler").setViewName("schedulerdetail");
	}

}
