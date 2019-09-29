package org.dante.springboot.config;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "shiroEntityManagerFactory", transactionManagerRef = "shiroTransactionManager", basePackages = { ShiroDataSourceConfig.JPA_DAO_PKG }) 
@MapperScan(basePackages = ShiroDataSourceConfig.MYBATIS_MAPPER_PKG, sqlSessionFactoryRef = "shiroSqlSessionFactory")
public class ShiroDataSourceConfig {

	protected static final String JPA_DAO_PKG = "org.dante.springboot.dao.shiro";
	protected static final String JPA_PO_PKG = "org.dante.springboot.po.shiro";
	
	protected static final String MYBATIS_BO_PKG = "org.dante.springboot.bo.shiro";
	protected static final String MYBATIS_MAPPER_PKG = "org.dante.springboot.mapper.shiro";
	protected static final String MYBATIS_XML_PKG = "classpath:mapper/shiro/*.xml";
	protected static final String MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private HibernateProperties hibernateProperties;

	@Autowired
	@Qualifier("shiroDataSource")
	private DataSource shiroDataSource;

	/**
	 * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 * 
	 * @return
	 */
	@Bean(name = "shiroEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean shiroEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
//		return builder.dataSource(shiroDataSource).properties(getVendorProperties(shiroDataSource))
		return builder.dataSource(shiroDataSource).properties(properties)
				.packages(ShiroDataSourceConfig.JPA_PO_PKG) // 设置实体类所在位置
				.persistenceUnit("shiroPersistenceUnit").build();
		// .getObject();//不要在这里直接获取EntityManagerFactory
	}

	/**
	 * Springboot 1.X
	 * 
	 * @param dataSource
	 * @return
	 */
	/*
	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}
	*/
	
	/**
	 * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
	 * 总之,在执行操作之前,我们总要获取一个EntityManager,这就类似于Hibernate的Session,
	 * mybatis的sqlSession.
	 * 
	 * @param builder
	 * @return
	 */
	@Bean(name = "shiroEntityManagerFactory")
	public EntityManagerFactory shiroEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return this.shiroEntityManagerFactoryBean(builder).getObject();
	}

	/**
	 * 配置事物管理器
	 * 
	 * @return
	 */
	@Bean(name = "shiroTransactionManager")
	public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(shiroEntityManagerFactory(builder));
	}

	/**
	 * Mybatis 配置
	 * 
	 */
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
