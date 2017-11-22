package org.dante.springboot.data;

import org.dante.springboot.data.pub.CustomRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// 使自定义的Repository起效
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class SpringbootDataApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootDataApplication.class, args);
	}
}
