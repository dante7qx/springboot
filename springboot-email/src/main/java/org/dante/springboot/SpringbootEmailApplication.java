package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 邮件集成 
 * 
 * http://www.ityouknow.com/springboot/2017/05/06/springboot-mail.html
 * https://javabydeveloper.com/spring-boot-email-template/
 * 
 * @author dante
 *
 */
@EnableAsync
@SpringBootApplication
public class SpringbootEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEmailApplication.class, args);
	}
}
