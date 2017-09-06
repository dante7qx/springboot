## SpringBoot 配置 SSL

1. 生成证书（在当前用户根目录下）
 ```    shell
 keytool -genkeypair -alias SpiritKey -keyalg RSA -keysize 2048 \
  -dname "CN=localhost,OU=HNA,O=eking,L=BeiJing,S=Beijing,C=CN" \
  -keypass 12,qw,as -keystore server.jks -storepass 1234@@qwer
 ```

2. 配置证书  
 ```  yaml
 server:  
  port: 8443  
  ssl:  
    key-store: classpath:server.jks  
    key-store-password: 1234@@qwer  
    key-password: 12,qw,as  
    key-alias: SpiritKey  
 ```

3. 安装证书

 - http://blog.csdn.net/catoop/article/details/51155224


4. http 转向 https

   配置 TomcatEmbeddedServletContainerFactory

```java
@Bean
public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
        @Override
        protected void postProcessContext(Context context) {
          SecurityConstraint securityConstraint = new SecurityConstraint();
          securityConstraint.setUserConstraint("CONFIDENTIAL");
          SecurityCollection collection = new SecurityCollection();
          collection.addPattern("/*");
          securityConstraint.addCollection(collection);
          context.addConstraint(securityConstraint);
        }
    };
    tomcat.addAdditionalTomcatConnectors(httpConnector());
    return tomcat;
}

@Bean
public Connector httpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8080);
    connector.setSecure(false);
    connector.setRedirectPort(8443);
    return connector;
}
```




