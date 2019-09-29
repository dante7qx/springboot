package org.dante.springboot.cert;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class CustomServletContainer {

	@Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
		return factory -> {
			factory.setPort(8081);
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
		};
		
		/*
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(8081);
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
            }
        };
        */
	}
	
	/**
	 * Springboot 1.X 
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8443);	// 优先级高于配置文件
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
		container.setSessionTimeout(10,TimeUnit.MINUTES);
		
	}
	*/


}
