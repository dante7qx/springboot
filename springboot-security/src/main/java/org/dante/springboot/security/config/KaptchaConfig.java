package org.dante.springboot.security.config;

import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class KaptchaConfig {
	@Bean
	public ServletRegistrationBean servletRegistrationBean() throws ServletException {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/images/kaptcha.jpg");
		servlet.addInitParameter("kaptcha.border", "no"/*kborder*/);//无边框  
        servlet.addInitParameter("kaptcha.textproducer.font.color", "black");  
        servlet.addInitParameter("kaptcha.textproducer.font.size", "30");  
        servlet.addInitParameter("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");  
        servlet.addInitParameter("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");  
        servlet.addInitParameter("kaptcha.image.width", "90");  
        servlet.addInitParameter("kaptcha.image.height", "35");  
        servlet.addInitParameter("kaptcha.textproducer.char.string", "0123456789");
        servlet.addInitParameter("kaptcha.textproducer.char.length", "4");  
        servlet.addInitParameter("kaptcha.textproducer.char.space", "3");  
        servlet.addInitParameter("kaptcha.background.clear.from", "255,255,255"); //和登录框背景颜色一致   
        servlet.addInitParameter("kaptcha.background.clear.to", "255,255,255");  
		return servlet;
	}
}
