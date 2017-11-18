package org.dante.springboot.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DruidDatasourceConfig {

	/**
	 * @Primary表示主数据源
	 * 
	 * @return
	 */
	@Primary
	@Bean
	@ConfigurationProperties("spring.datasource.druid.springboot")
	public DataSource springbootDataSource(){
	    return DruidDataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource.druid.shiro")
	public DataSource shiroDataSource(){
	    return DruidDataSourceBuilder.create().build();
	}
	
}
