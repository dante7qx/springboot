package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 前端同域 + 后端同 Redis （共享 Cookie 同步会话）
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootSSOServerApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSSOServerApplication.class, args);
		System.out.println("\n------ Sa-Token-SSO 认证中心启动成功");
	}
}
