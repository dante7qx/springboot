package org.dante.springboot;

import org.dante.springboot.prop.SpiritProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * springboot 整合 log4j2 
 *     https://www.cnblogs.com/xishuai/p/spring-boot-log4j2.html
 *     https://blog.lqdev.cn/2018/08/22/springboot/chapter-twenty-three/
 * log4j2 的详细配置：
 *     https://blog.51cto.com/1197822/2157668
 *     https://gsealy.cn/posts/81f201e1/
 * 
 * @author dante
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(SpiritProperties.class)
public class SpringbootLog4j2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLog4j2Application.class, args);
	}
}
