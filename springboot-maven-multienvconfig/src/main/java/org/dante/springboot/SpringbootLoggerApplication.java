package org.dante.springboot;

import org.dante.springboot.prop.SpiritProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 参考资料：
 *   1. https://github.com/LeahShi/blog/issues/5
 *   2. https://www.cnblogs.com/haixiang/p/12451703.html
 * 
 * @author dante
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(SpiritProperties.class)
public class SpringbootLoggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLoggerApplication.class, args);
	}
}
