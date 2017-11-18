package org.dante.springboot.config;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "shiroEntityManagerFactory", transactionManagerRef = "shiroTransactionManager", basePackages = {
		"org.dante.springboot.dao.shiro" }) // 设置Repository所在位置
public class ShiroDataSourceConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	@Qualifier("shiroDataSource")
	private DataSource shiroDataSource;

	/**
	 * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 * 
	 * @return
	 */
	@Bean(name = "shiroEntityManagerFactoryBean")
	// @Primary
	public LocalContainerEntityManagerFactoryBean shiroEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(shiroDataSource).properties(getVendorProperties(shiroDataSource))
				.packages("org.dante.springboot.po.shiro") // 设置实体类所在位置
				.persistenceUnit("shiroPersistenceUnit").build();
		// .getObject();//不要在这里直接获取EntityManagerFactory
	}

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

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

}
