## Spring Retry 重试机制
### 一.  概述

Spring Retry 提供了自动重新调用失败操作的能力。 这在错误可能是暂时的（如瞬时网络故障）的情况下很有帮助。

### 二.  技术原理

Spring Retry 是通过 AOP 来实现对重试方法的切入，然后进行代理以实现方法重试。重试方法执行在当前线程下，所以重试过程中当前线程会堵塞。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy(proxyTargetClass = false)		// AspectJ
@Import(RetryConfiguration.class)
@Documented
public @interface EnableRetry {
	/**
	 * Indicate whether subclass-based (CGLIB) proxies are to be created as opposed to
	 * standard Java interface-based proxies. The default is {@code false}.
	 * @return whether to proxy or not to proxy the class
	 */
	boolean proxyTargetClass() default false;
}

```

### 三.  开发说明

#### 1. 引入依赖

```xml
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
</dependency>
```

#### 2. 开启重试

```java
// 开启重试的支持，只有注解方式进行重试的时候需要
@EnableRetry
@SpringBootApplication
public class SpringbootRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRetryApplication.class, args);
	}
}
```

#### 3. 注解说明

- 重试注解

| **重试注解**    | **注解说明**                                                 |
| --------------- | ------------------------------------------------------------ |
| @EnableRetry    | 开启重试，proxyTargetClass属性为true时（默认false），使用CGLIB代理 |
| @Retryable      | 注解需要被重试的方法                                         |
| @Backoff        | 重试回退策略，（立即重试还是等待一会再重试）                 |
| @Recover        | 用于方法，用于@Retryable失败时的“兜底”处理方法               |
| @CircuitBreaker | 用于方法，实现熔断模式                                       |

- 使用注解进行重试时需要注意
  1. 在同一个类中方法调用重试方法无效
  2. 静态方法无效
  3. @Recover方法返回值类型要和重试方法返回值类型相同

### 四.  参考资料 
- https://blog.csdn.net/qq330983778/article/details/112758654
- https://www.baeldung.com/spring-retry
