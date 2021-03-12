package org.dante.springboot.docker.start;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class TestStarted implements ServletContextAware {

	/**
     * 在填充普通bean属性之后但在初始化之前调用
     * 类似于initializingbean的afterpropertiesset或自定义init方法的回调
     *
     */
	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("该方法会在填充完普通Bean的属性，但是还没有进行Bean的初始化之前执行");
	}

}
