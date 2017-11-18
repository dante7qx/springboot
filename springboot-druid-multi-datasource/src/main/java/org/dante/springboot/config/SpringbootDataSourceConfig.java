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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "springbootEntityManagerFactory", transactionManagerRef = "springbootTransactionManager", basePackages = {
		"org.dante.springboot.dao.springboot" }) // 设置Repository所在位置
public class SpringbootDataSourceConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	@Qualifier("springbootDataSource")
	private DataSource springbootDataSource;

	/**
	 * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
	 * 
	 * @return
	 */
	@Bean(name = "springbootEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean springbootEntityManagerFactoryBean(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(springbootDataSource).properties(getVendorProperties(springbootDataSource))
				.packages("org.dante.springboot.po.springboot") // 设置实体类所在位置
				.persistenceUnit("springbootPersistenceUnit").build();
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
	@Bean(name = "springbootEntityManagerFactory")
	@Primary
	public EntityManagerFactory springbootEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return this.springbootEntityManagerFactoryBean(builder).getObject();
	}

	/**
	 * 配置事物管理器
	 * 
	 * @return
	 */
	@Bean(name = "springbootTransactionManager")
	@Primary
	public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(springbootEntityManagerFactory(builder));
	}

}
