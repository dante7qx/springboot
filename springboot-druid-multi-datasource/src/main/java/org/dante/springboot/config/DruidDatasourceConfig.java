package org.dante.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DruidDatasourceConfig {

	@Bean
	@Primary
	@Qualifier("springbootDataSource")
	@ConfigurationProperties("spring.datasource.druid.springboot")
	public DataSource springbootDataSource(){
	    return DruidDataSourceBuilder.create().build();
	}
	
	@Bean
    @Primary
    @Qualifier("springboot2DataSource")
	@ConfigurationProperties("spring.datasource.druid.springboot2")
	public DataSource shiroDataSource(){
	    return DruidDataSourceBuilder.create().build();
	}
	
}
