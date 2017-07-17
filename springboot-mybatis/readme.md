# Spring Boot Mybatis

1. 添加依赖

   ```xml
   <dependency>
     	<groupId>org.mybatis.spring.boot</groupId>
     	<artifactId>mybatis-spring-boot-starter</artifactId>
     	<version>${mybatis.version}</version>
   </dependency>
   <!-- 分页插件 -->
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
       <version>${pagehelper.version}</version>
   </dependency>
   ```

2. 启动类

   ```java
   @SpringBootApplication
   @EnableTransactionManagement	// 事务支持
   @MapperScan("org.dante.springboot.dao")
   public class SpringbootMybatisApplication {
   	public static void main(String[] args) {
   		SpringApplication.run(SpringbootMybatisApplication.class, args);
   	}
   }
   ```

3. 配置

   ```yaml
   mybatis:
     mapper-locations:
       - classpath:mybatis/mapper/*.xml
     config-location: classpath:mybatis/mybatis-config.xml
     type-aliases-package: org.dante.springboot.po
   ```

4. 事务控制

   - 启动类：添加注解 `@EnableTransactionManagement`

   - Service：同 spring-data-jpa 事务

     ```java
     @Transactional(readOnly=true)
     @Service
     public class TeacherStudentService {
     	@Transactional
     	public void presistTeacherStudent() {......}
     }
     ```

5. 缓存
	- 添加配置：`<cache eviction="LRU" flushInterval="30000" size="1024" readOnly="true"/>，简写 <cache />`
	- 注意：由于二级缓存的数据不一定都是存储到内存中，它的存储介质多种多样，所以需要给缓存的对象执行序列化。
	- 禁用缓存：`useCache="false"`

6. 参考文档

   - http://www.mybatis.org/mybatis-3/zh/
   - http://www.jianshu.com/p/e09d2370b796