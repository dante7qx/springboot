## Flowable 工作流

### 一. 简介

Flowable是一个使用Java编写的轻量级业务流程引擎。Flowable流程引擎可用于部署BPMN 2.0流程定义（用于定义流程的行业XML标准）， 创建这些流程定义的流程实例，进行查询，访问运行中或历史的流程实例与相关数据，等等。

Flowable项目源自于Activiti，通过两个框架的发展史即知。在2016.7~2017.5期间Activiti团队内部已经产生了重大的分歧，于是原班核心人员（Activiti5以及6比较核心的leader）Tijs Rademakers和Joram Barrez等便去开发Flowable框架了，原来的Activiti6以及Activiti5代码则留给 Salaboy团队进行开发和维护。Flowable是基于`Activiti-6.0.0.Beta4`分支开发的。目前Flowable已经修复了Activiti6很多的bug，可以实现零成本从Activiti迁移到Flowable。

### 二. Springboot 整合

Springboot的版本为2.5.9，flowable的版本是6.7.2，mysql版本是5.7，mysql-connector驱动版本8.0.28，mybatis版本3.5.9。

#### 1. 集成flowable

添加依赖 flowable-spring-boot-starter，启动程序，Flowable会创建23张 ACT_ 开头的表。

```xml
<properties>
		<flowable.version>6.7.2</flowable.version>
</properties>

<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter</artifactId>
    <version>${flowable.version}</version>
</dependency>
```

添加配置

```yaml
## 创建完数据库后，关闭自动更新。原因是更新的标准并非是你引入的流程引擎的版本，而是官方发布的版本，所以如果一直开启，以后重启之类的可能导致提示版本升级失败，毕竟你的依赖版本并没有升级
flowable:
  database-schema-update: true
```

**问题： **mysql-connector 6.x 以上版本中，nullCatalogMeansCurrent **（mysql默认当前数据库操作）**参数默认是 false。Mysql使用schema标识库名而不是catalog，因此mysql会扫描所有的库来找表。如果其他库中有相同名称的表，flowable 就以为找到了，本质上这个表在当前数据库中并不存在。

**解决：**设置 nullCatalogMeansCurrent = true

1. mysql的连接字符串中添加上nullCatalogMeansCurrent=true。

2. hikari 数据源，设置 `data-source-properties.nullCatalogMeansCurrent: true`。

#### 2. 集成flowable-ui

（1）添加依赖

```xml
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter-ui-idm</artifactId>
    <version>${flowable.version}</version>
</dependency>
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter-ui-modeler</artifactId>
    <version>${flowable.version}</version>
</dependency>
<!-- 必须添加Guava依赖，flowable-ui中用到了 -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>31.1-jre</version>
</dependency>
```

（2）添加配置

```yaml
flowable:
  database-schema-update: true
  common:
    app:
      idm-url: /idm
  idm:
    app:
      admin:
        user-id: admin
        password: iamdante
# 第一次启动需要设置
#       first-name: 管理员
#       last-name: 超级
  modeler:
    app:
      rest-enabled: true
```

（3）页面集成

- 下载 flowable-engine 源码中的前端文件，拷贝到自己的项目下。（https://github.com/flowable/flowable-engine）

​	    将 `flowable-idm-frontend`、`flowable-modeler-frontend`、`flowable-task-frontend`到自己的项目 static 目录下。

- 设置路由跟资源路径绑定

```java
@Configuation
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/modeler/**").addResourceLocations("classpath:/static/modeler/");
      registry.addResourceHandler("/idm/**").addResourceLocations("classpath:/static/idm/");
    }
}
```



### 八. 参考文档

#### Flowable文档

- 

#### Springboot 集成

- https://www.modb.pro/db/138760
- https://blog.csdn.net/ooaash/article/details/120724635
- https://linjinp.blog.csdn.net/article/details/102851433?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-5.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-5.pc_relevant_default&utm_relevant_index=9

