package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;

@SpringBootApplication
public class SpringbootSpecialToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSpecialToolApplication.class, args);
	}


	/**
	 * 使用 JDK HttpClient 作为 RestTemplate 替代方案
	 * 支持 HTTP/2、同步/异步请求、连接超时配置等
	 */
	@Bean
	public HttpClient httpClient() {
		return HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2) // 启用 HTTP/2
				.connectTimeout(java.time.Duration.ofSeconds(5)) // 连接超时
				.build();
	}
}
