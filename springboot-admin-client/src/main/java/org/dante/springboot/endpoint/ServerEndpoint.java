package org.dante.springboot.endpoint;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ServerEndpoint extends AbstractEndpoint<String> implements ApplicationContextAware {
	
	private ApplicationContext context;
	
	public ServerEndpoint(String id) {
		super(id);
	}

	@Override
	public String invoke() {
		String appName = context.getEnvironment().getProperty("spring.application.name");
		return "Spirit Endpoint -> " + appName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
