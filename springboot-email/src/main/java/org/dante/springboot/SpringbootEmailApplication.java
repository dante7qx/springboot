package org.dante.springboot;

import org.dante.springboot.email.prop.EmailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 邮件集成 http://www.ityouknow.com/springboot/2017/05/06/springboot-mail.html
 * 
 * @author dante
 *
 */
@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
public class SpringbootEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEmailApplication.class, args);
	}
}
