package org.dante.springboot;

import org.dante.springboot.prop.DBProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DBProp.class)
public class SpringbootApolloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApolloApplication.class, args);
	}

}
