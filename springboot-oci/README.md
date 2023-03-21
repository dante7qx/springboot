## Springboot 构建 OCI 镜像

### 一.  概述

在 `Springboot 2.3+`后，不需要再通过编写`Dockerfile`的方式来生成`Springboot`项目的 `Image`。

### 二.  技术原理

`Springboot` 通过 `Buildpacks`（一种提供框架和应用程序依赖性的工具）来构建镜像。

### 三.  开发说明

``` bash
## 构建 fatjar
mvn clean package -Dmaven.test.skip=true

## 构建镜像（会自动构建出 fatjar）
mvn spring-boot:build-image
```

**配置说明：**

1. 每次打包都会生成 fatjar 和 image。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>build-image</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

2. 要执行`mvn spring-boot:build-image`才会生成镜像。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

3. 添加配置参数

```xml
<configuration>
  	<image>
        <name>dante2012/${project.artifactId}:${project.version}</name>
        <env>
            <BPE_DELIM_JAVA_TOOL_OPTIONS xml:space="preserve"> </BPE_DELIM_JAVA_TOOL_OPTIONS>
            <BPE_APPEND_JAVA_TOOL_OPTIONS>-XX:MinRAMPercentage=75.0 -XX:MaxRAMPercentage=75.0 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m</BPE_APPEND_JAVA_TOOL_OPTIONS>
        </env>
  	</image>
</configuration>
```



### 四.  参考资料 

- https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/htmlsingle/#build-image
- https://www.baeldung.com/spring-boot-docker-images
