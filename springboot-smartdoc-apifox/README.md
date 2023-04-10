## Smart-doc + APIFox

### 一.  概述

Springboot项目使用smart-doc+Apifox 便捷生成管理接口文档。

### 二.  技术原理

### 三.  开发说明

```sh
//生成html
mvn -Dfile.encoding=UTF-8 smart-doc:html
//生成markdown
mvn -Dfile.encoding=UTF-8 smart-doc:markdown
//生成adoc
mvn -Dfile.encoding=UTF-8 smart-doc:adoc
//生成postman json数据
mvn -Dfile.encoding=UTF-8 smart-doc:postman
// 生成 Open Api 3.0+,Since smart-doc-maven-plugin 1.1.5
mvn -Dfile.encoding=UTF-8 smart-doc:openapi
```



### 四.  参考资料 

- https://smart-doc-group.github.io/#/
- https://blog.csdn.net/qq_37132495/article/details/122569906
