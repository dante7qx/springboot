# Spring boot mybatis

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

5. 参考文档

   - http://www.mybatis.org/mybatis-3/zh/