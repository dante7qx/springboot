package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 解决使用Spring Boot、Multipartfile上传文件路径错误问题
 * http://blog.csdn.net/daniel7443/article/details/51620308
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootOfficeApplication.class, args);
	}

}
