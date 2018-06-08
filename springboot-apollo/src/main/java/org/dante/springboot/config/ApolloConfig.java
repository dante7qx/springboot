package org.dante.springboot.config;

import org.dante.springboot.prop.DBProp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@Configuration
@EnableApolloConfig
public class ApolloConfig {
	
	@Bean
	public DBProp dbProp() {
		return new DBProp();
	}
}
