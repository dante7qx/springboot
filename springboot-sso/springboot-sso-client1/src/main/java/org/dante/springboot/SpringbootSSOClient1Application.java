package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目。在尽可能节约内存的情况下支持读写百M的Excel。
 * 
 * https://www.yuque.com/easyexcel/doc/easyexcel
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootSSOClient1Application {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSSOClient1Application.class, args);
		System.out.println("\nSa-Token SSO模式一 Client1端启动成功");
	}
}
