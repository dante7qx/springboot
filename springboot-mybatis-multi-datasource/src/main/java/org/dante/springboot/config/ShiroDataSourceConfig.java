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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = ShiroDataSourceConfig.MYBATIS_MAPPER_PKG, sqlSessionFactoryRef = "shiroSqlSessionFactory")
public class ShiroDataSourceConfig {

	protected static final String MYBATIS_BO_PKG = "org.dante.springboot.bo.shiro";
	protected static final String MYBATIS_MAPPER_PKG = "org.dante.springboot.mapper.shiro";
	protected static final String MYBATIS_XML_PKG = "classpath:mapper/shiro/*.xml";
	protected static final String MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	
	@Autowired
	@Qualifier("shiroDataSource")
	private DataSource shiroDataSource;

	@Bean(name = "shiroTransactionManager")
    public DataSourceTransactionManager shiroTransactionManager() {
        return new DataSourceTransactionManager(shiroDataSource);
    }

	@Bean(name = "shiroSqlSessionFactory")
    public SqlSessionFactory springSqlSessionFactory()
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(shiroDataSource);
        sessionFactory.setVfs(SpringBootVFS.class); 
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(ShiroDataSourceConfig.MYBATIS_XML_PKG));
        sessionFactory.setConfigLocation(resolver.getResource(ShiroDataSourceConfig.MYBATIS_CONFIG));
        sessionFactory.setTypeAliasesPackage(ShiroDataSourceConfig.MYBATIS_BO_PKG);
        return sessionFactory.getObject();
    }
}
