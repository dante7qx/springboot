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
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "springbootEntityManagerFactory", transactionManagerRef = "springbootTransactionManager", basePackages = {SpringbootDataSourceConfig.JPA_DAO_PKG}) // 设置Repository所在位置
@MapperScan(basePackages = SpringbootDataSourceConfig.MYBATIS_MAPPER_PKG, sqlSessionFactoryRef = "springbootSqlSessionFactory")
public class SpringbootDataSourceConfig {

	protected static final String JPA_DAO_PKG = "org.dante.springboot.dao.springboot";
	protected static final String JPA_PO_PKG = "org.dante.springboot.po.springboot";
	
	protected static final String MYBATIS_BO_PKG = "org.dante.springboot.bo.springboot";
	protected static final String MYBATIS_MAPPER_PKG = "org.dante.springboot.mapper.springboot";
	protected static final String MYBATIS_XML_PKG = "classpath:mapper/springboot/*.xml";
	protected static final String MYBATIS_CONFIG = "classpath:mybatis-config.xml";
	
	@Autowired
	private JpaProperties jpaProperties;
//	
//	@Autowired
//	private HibernateProperties hibernateProperties;
	
	@Autowired
	@Qualifier("springbootDataSource")
	private DataSource springbootDataSource;
	
	/**
	 * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 * 
	 * @return
	 */
//	@Bean(name = "springbootEntityManagerFactoryBean")
//	public LocalContainerEntityManagerFactoryBean springbootEntityManagerFactoryBean(
//			EntityManagerFactoryBuilder builder) {
//		Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
////		return builder.dataSource(springbootDataSource).properties(getVendorProperties(springbootDataSource))
//		return builder.dataSource(springbootDataSource).properties(properties)
//				.packages(SpringbootDataSourceConfig.JPA_PO_PKG) // 设置实体类所在位置
//				.persistenceUnit("springbootPersistenceUnit").build();
//		// .getObject();//不要在这里直接获取EntityManagerFactory
//	}
	
	@Bean(name = "springbootEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean springbootEntityManagerFactoryBean() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(jpaProperties.isShowSql());
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(springbootDataSource);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan(SpringbootDataSourceConfig.JPA_PO_PKG);
		factoryBean.setJpaPropertyMap(jpaProperties.getProperties());
		factoryBean.setPersistenceUnitName("springbootPersistenceUnit");
		
		return factoryBean;
	}

	/** Springboot 1.X
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
	@Bean(name = "springbootEntityManagerFactory")
	@Primary
	public EntityManagerFactory springbootEntityManagerFactory() {
		return this.springbootEntityManagerFactoryBean().getObject();
	}

	/**
	 * 配置事物管理器
	 * 
	 * @return
	 */
	@Bean(name = "springbootTransactionManager")
	@Primary
	public PlatformTransactionManager writeTransactionManager() {
		return new JpaTransactionManager(springbootEntityManagerFactory());
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
