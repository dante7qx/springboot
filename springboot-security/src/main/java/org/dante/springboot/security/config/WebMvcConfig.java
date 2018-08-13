package org.dante.springboot.security.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/mainpage").setViewName("mainpage");
		
		registry.addViewController("/error/404").setViewName("common/error/404");
		registry.addViewController("/error/500").setViewName("common/error/500");
		
//		registry.addViewController("/login").setViewName("login");
//		registry.addViewController("/loginpage").setViewName("login");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/user").setViewName("user/userlist");
		registry.addViewController("/edituser").setViewName("user/userdetail");
		registry.addViewController("/user2").setViewName("user/user2");
		
		registry.addViewController("/authority").setViewName("authority/authoritymanager");
		
		registry.addViewController("/role").setViewName("role/rolelist");
		registry.addViewController("/editrole").setViewName("role/roledetail");
		
		registry.addViewController("/menu").setViewName("resource/resourcemanager");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer(){
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
                container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/error/500"));
            }
        };
    }
}
