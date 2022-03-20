package org.dante.springboot.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "springboot2EntityManagerFactory", transactionManagerRef = "springboot2TransactionManager", basePackages = { Springboot2DataSourceConfig.JPA_DAO_PKG }) 
@MapperScan(basePackages = Springboot2DataSourceConfig.MYBATIS_MAPPER_PKG, sqlSessionFactoryRef = "springboot2SqlSessionFactory")
public class Springboot2DataSourceConfig {

	protected static final String JPA_DAO_PKG = "org.dante.springboot.dao.springboot2";
	protected static final String JPA_PO_PKG = "org.dante.springboot.po.springboot2";
	
	protected static final String MYBATIS_BO_PKG = "org.dante.springboot.bo.springboot2";
	protected static final String MYBATIS_MAPPER_PKG = "org.dante.springboot.mapper.springboot2";
	protected static final String MYBATIS_XML_PKG = "classpath:mapper/springboot2/*.xml";
	protected static final String MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	
	@Autowired
	private JpaProperties jpaProperties;
//	
//	@Autowired
//	private HibernateProperties hibernateProperties;

	@Autowired
	@Qualifier("springboot2DataSource")
	private DataSource springboot2DataSource;

	/**
	 * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 * 
	 * @return
	 */
//	@Bean(name = "springboot2EntityManagerFactoryBean")
//	public LocalContainerEntityManagerFactoryBean springboot2EntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
////		return builder.dataSource(springboot2DataSource).properties(getVendorProperties(springboot2DataSource))
//		return builder.dataSource(springboot2DataSource)
//				.properties(properties)
//				.packages(Springboot2DataSourceConfig.JPA_PO_PKG) // 设置实体类所在位置
//				.persistenceUnit("springboot2PersistenceUnit").build();
//		// .getObject();//不要在这里直接获取EntityManagerFactory
//	}
	
	@Bean(name = "springboot2EntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean springboot2EntityManagerFactoryBean() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(springboot2DataSource);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan(Springboot2DataSourceConfig.JPA_PO_PKG);
		factoryBean.setJpaPropertyMap(jpaProperties.getProperties());
		factoryBean.setPersistenceUnitName("springboot2PersistenceUnit");

		return factoryBean;
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
	@Bean(name = "springboot2EntityManagerFactory")
	public EntityManagerFactory springboot2EntityManagerFactory() {
		return this.springboot2EntityManagerFactoryBean().getObject();
	}

	/**
	 * 配置事物管理器
	 * 
	 * @return
	 */
	@Bean(name = "springboot2TransactionManager")
	public PlatformTransactionManager writeTransactionManager() {
		return new JpaTransactionManager(springboot2EntityManagerFactory());
	}

	/**
	 * Mybatis 配置
	 * 
	 */
	@Bean(name = "springboot2SqlSessionFactory")
    public SqlSessionFactory springSqlSessionFactory()
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(springboot2DataSource);
        sessionFactory.setVfs(SpringBootVFS.class); 
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(Springboot2DataSourceConfig.MYBATIS_XML_PKG));
        sessionFactory.setConfigLocation(resolver.getResource(Springboot2DataSourceConfig.MYBATIS_CONFIG));
        sessionFactory.setTypeAliasesPackage(Springboot2DataSourceConfig.MYBATIS_BO_PKG);
        return sessionFactory.getObject();
    }
}
