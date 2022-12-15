package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 前端不同域 + 后端同 Redis （URL重定向传播会话）
 * 
 * 在跨域模式下，"共享Cookie方案" 的失效
 * 
 * 1. 用户在 子系统 点击 [登录] 按钮。
 * 2. 用户跳转到子系统登录接口 /sso/login，并携带 back参数 记录初始页面URL。
	  形如：http://{sso-client}/sso/login?back=xxx
 * 3. 子系统检测到此用户尚未登录，再次将其重定向至SSO认证中心，并携带redirect参数记录子系统的登录页URL。
	  形如：http://{sso-server}/sso/auth?redirect=xxx?back=xxx
 * 4. 用户进入了 SSO认证中心 的登录页面，开始登录。
 * 5. 用户 输入账号密码 并 登录成功，SSO认证中心再次将用户重定向至子系统的登录接口/sso/login，并携带ticket码参数。
	  形如：http://{sso-client}/sso/login?back=xxx&ticket=xxxxxxxxx
 * 6. 子系统根据 ticket码 从 SSO-Redis 中获取账号id，并在子系统登录此账号会话。
 * 7. 子系统将用户再次重定向至最初始的 back 页面。
 * 
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootSSO2ServerApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSSO2ServerApplication.class, args);
		System.out.println("\n------ Sa-Token-SSO 认证中心启动成功");
	}
}
