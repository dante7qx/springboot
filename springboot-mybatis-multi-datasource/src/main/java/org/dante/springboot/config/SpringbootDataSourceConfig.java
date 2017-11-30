package org.dante.springboot.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = SpringbootDataSourceConfig.MYBATIS_MAPPER_PKG, sqlSessionFactoryRef = "springbootSqlSessionFactory")
public class SpringbootDataSourceConfig {

	protected static final String MYBATIS_BO_PKG = "org.dante.springboot.bo.springboot";
	protected static final String MYBATIS_MAPPER_PKG = "org.dante.springboot.mapper.springboot";
	protected static final String MYBATIS_XML_PKG = "classpath:mapper/springboot/*.xml";
	protected static final String MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	
	@Autowired
	@Qualifier("springbootDataSource")
	private DataSource springbootDataSource;
	
	
	@Bean(name = "springbootTransactionManager")
	@Primary
    public DataSourceTransactionManager springbootTransactionManager() {
        return new DataSourceTransactionManager(springbootDataSource);
    }

	/**
	 * Mybatis 配置
	 * 
	 */
	@Bean(name = "springbootSqlSessionFactory")
    @Primary
    public SqlSessionFactory springSqlSessionFactory()
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(springbootDataSource);
        sessionFactory.setVfs(SpringBootVFS.class); 
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(SpringbootDataSourceConfig.MYBATIS_XML_PKG));
        sessionFactory.setConfigLocation(resolver.getResource(SpringbootDataSourceConfig.MYBATIS_CONFIG));
        sessionFactory.setTypeAliasesPackage(SpringbootDataSourceConfig.MYBATIS_BO_PKG);
        return sessionFactory.getObject();
    }
}
