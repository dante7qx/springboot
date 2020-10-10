package org.dante.springboot.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
@Endpoint(id = "spirit-endpoint")
public class ServerEndpoint implements ApplicationContextAware {

	private ApplicationContext context;

	@ReadOperation
	public Map<String, Object> endpoint() {
		Map<String, Object> map = new HashMap<>(16);
		String appName = context.getEnvironment().getProperty("spring.application.name");
		map.put("message", "Spirit Endpoint -> " + appName);
		return map;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
