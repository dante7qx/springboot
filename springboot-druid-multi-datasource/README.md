## Springboot Druid Jpa 多数据源配置

### 1. 引入Druid

在 Spring Boot 项目中加入`druid-spring-boot-starter`依赖

```xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.5</version>
</dependency>
```

### 2. 配置属性

####  JDBC 配置

```properties
spring.datasource.druid.url= # 或spring.datasource.url= 
spring.datasource.druid.username= # 或spring.datasource.username=
spring.datasource.druid.password= # 或spring.datasource.password=
spring.datasource.druid.driver-class-name= #或 spring.datasource.driver-class-name=
```

#### 多数据源配置

Druid 数据源配置，继承spring.datasource.* 配置，相同则覆盖

```yaml
spring:
  datasource:
    druid: 
      driver-class-name: com.mysql.jdbc.Driver
      initial-size: 10
      min-idle: 10
      max-active: 30
      max-wait: 60000
      min-evictable-idle-time-millis: 300000
      pool-prepared-statements: false
      max-pool-prepared-statement-per-connection-size: 20
      validation-query: select 1
      test-on-borrow: false
      test-on-return: false
      test-while-idle: true
      filter:
            stat:
              db-type: mysql
              log-slow-sql: true
              slow-sql-millis: 2000
            wall:
              enabled: true
              db-type: mysql
              config:
                multi-statement-allow: true
      web-stat-filter:
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'
      stat-view-servlet:
        login-username: druid
        login-password: 123456
      springboot: ## springboot数据源，继承 spring.datasource.druid.* 配置，相同则覆盖
        url: jdbc:mysql://localhost/springboot
        username: root
        password: iamdante
      shiro: ## shiro数据源，继承 spring.datasource.druid.* 配置，相同则覆盖
        url: jdbc:mysql://localhost/shiro
        username: root
        password: iamdante
```

#### JavaConfig

##### 总数据源配置

```java
@Configuration
public class DruidDatasourceConfig {
    // @Primary表示主数据源
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
```

##### springboot数据源

​	LocalContainerEntityManagerFactoryBean 和 EntityManagerFactory 方法其中一个注解@Primary即可，不然启动会报错。

```java
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="springbootEntityManagerFactory",
        transactionManagerRef="springbootTransactionManager",
        basePackages= { "org.dante.springboot.dao.springboot" }) //设置Repository所在位置
public class SpringbootDataSourceConfig {

	@Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("springbootDataSource")
    private DataSource springbootDataSource;
  
    /**
     * 通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
     * @return
     */
    @Bean(name = "springbootEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean springbootEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(springbootDataSource)
                .properties(getVendorProperties(springbootDataSource))
                .packages("org.dante.springboot.po.springboot") //设置实体类所在位置
                .persistenceUnit("springbootPersistenceUnit")
                .build();
    }
  
    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }
  
    /**
     * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
     * 总之,在执行操作之前,总要获取一个EntityManager,这就类似于Hibernate的Session,
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
     * @return
     */
    @Bean(name = "springbootTransactionManager")
    @Primary
    public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(springbootEntityManagerFactory(builder));
    }
	
}
```

##### shiro数据源

​	注意 @Primary 的使用，shiro不是主数据源，EntityManagerFactory、PlatformTransactionManager不要配置@Primary 。

```java
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
	public LocalContainerEntityManagerFactoryBean shiroEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(shiroDataSource).properties(getVendorProperties(shiroDataSource))
				.packages("org.dante.springboot.po.shiro") // 设置实体类所在位置
				.persistenceUnit("shiroPersistenceUnit").build();
	}

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

	/**
	 * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
	 * 总之,在执行操作之前,总要获取一个EntityManager,这就类似于Hibernate的Session,
	 * mybatis的sqlSession.
	 * 
	 * @param builder
	 * @return
	 */
	@Bean(name = "shiroEntityManagerFactory")
	public EntityManagerFactory shiroEntityManagerFactory(EntityManagerFactoryBuilder builder){
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
```

### 3. 参考资料

- https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
- http://www.jianshu.com/p/9f812e651319