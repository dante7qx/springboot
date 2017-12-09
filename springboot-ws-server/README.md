## Spring Web Services

### 一. 背景

#### 1. SOA 

​	Service Oriented Architecture ，面向服务的架构。包含了运行环境、编程模型、架构风格和相关方法论等在内的一整套分布式软件系统构造方法和环境，涵盖服务的整个生命周期。服务做为SOA的核心，构建在一系列基于开放标准的基础之上。而 Web Service 定义了如何在异构系统之间实现通信的标准化方法，可以跨越运行平台和实现语言。因此 Web Service 成为了实现 SOA 中服务的主要技术。

#### 2. WebService（SOAP）

​	WebServices 提供一个建立分布式应用的平台，使得运行在不同操作系统和不同设备上的软件，或者是用不同的程序语言和不同厂商的软件开发工具开发的软件，所有可能的已开发和部署的软件，能够利用这一平台实现分布式计算的目的。WebServices的思想是：使得应用程序也具有 Web 分布式编程模型的松散耦合性。

**特点**

- **WebServices** 是自包含的**

即在客户端不需要附加任何软件，只要客户机支持 HTTP 和XML。

 -  **WebServices 是自我描述的**

    在客户端和服务端都不需要知道除了请求和响应消息的格式和内容外的任何事。

- **WebServices 是跨平台和跨语言的**

- **WebServices 是基于开放和标准的**

- **WebServices 是动态的**

- **WebServices 是可以组合的**

  通过一个 WebService 访问另外一个 WebService 来达到组合的目的。通过组合 WebServices 便可以将简单的 WebServices 聚合成为实现更多复杂功能的复杂的服务。

- **WebServices 是松散耦合的**

  客户端和服务端无需知道对方具体实现。

- **WebServices 提供编程访问的能力**

  可以通过编写程序来访问Web Service。

- **WebServices 通过网络进行发布，查找和使用**


**组成**

- **SOAP** - 即 Simple Object Access Protocol，也就是简单对象访问协议。基于XML和HTTP，通过XML进行消息描述，然后通过HTTP进行消息传输。语法规则：
  - SOAP消息必须用XML来编码
  - SOAP消息必须使用SOAP Envelope命名空间
  - SOAP消息必须使用SOAP Encoding命名空间
  - SOAP消息不能包含DTD引用
  - SOAP消息不能包含XML处理指令
- **WSDL** - 即 Web Services Description Language也就是 Web 服务描述语言。基于 XML的用于描述 Web 服务以及如何访问 Web 服务的语言，可以绕过防火墙进行访问。主要包含：
  - 服务所提供的操做
  - 如何访问服务
  - 服务位于何处
- **UDDI** -  即 Universal Description，Discovery and Integration，也就是通用的描述，发现以及整合。用来管理、分发和查询Web Service。

### 二. Spring-WS

#### 1. 介绍

​	Spring Web服务（Spring-WS）是Spring社区致力于创建文档驱动的Web服务的产物。 Spring Web Services旨在促进 **Contract-First** 的SOAP服务开发，允许使用多种方法之一来创建灵活的Web服务来操纵XML有效载荷。 该产品基于Spring本身，这意味着可以将Spring概念（如IOC、AOP等）用作Web服务的组成部分。

-  **Contract-First**

  ​	开发者基于WSDL规范来编写将要暴露的Web Service。Contract-First的方式适用于你已经定义好Web Service操作的输入/输出的消息格式，或者想更好的控制XML与JAVA对象之间的映射关系

- **Contract-First**（**Code-Last**）

  ​	先创建Java类然后基于Java类生成Web Service组件。一般用于输入/输出对象格式简单，并希望快速部署应用。Code-First的方式正如开发普通的JAVA应用一样，不用关心WSDL和XSD的生成，也不必维护JAVA对象和XML元素的映射关系。

- **核心模块**

  ![springws-deps](/Users/dante/Documents/Project/spring/springboot/springboot-ws-server/springws-deps.png)

#### 2. 核心概念

##### Endpoint

​	用来处理XML请求，通过在类上添加 @Endpoint来实现。根据源码可以看出，Endpoint类是一种特殊的@Component，故可以注入其他Spring Bean。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Endpoint {}
```

##### Endpoint mapping

​	负责根据请求XML参数，映射到具体的 Endpoint 方法来处理。

##### PayloadRoot

​	@PayloadRoot方法注解，指明方法用于处理何种类型的XML	，通过属性 namespace 和 localPart。

`@PayloadRoot(namespace = "http://org.dante.springboot/ws", localPart = "getTestRequest")`，即指明该方法会处理XML格式是 getTestRequest，命名空间是 http://org.dante.springboot/ws 的请求。

​	处理返回时，除了 void 类型，均要使用 @ResponsePayload。

##### WebServiceMessage

​	表示具体的处理协议，Spring WS 支持如下：

| Source/Result implementation             | Wraps XML representation                 |
| ---------------------------------------- | ---------------------------------------- |
| `javax.xml.transform.dom.DOMSource`      | `org.w3c.dom.Node`                       |
| `javax.xml.transform.dom.DOMResult`      | `org.w3c.dom.Node`                       |
| `javax.xml.transform.sax.SAXSource`      | `org.xml.sax.InputSource` and `org.xml.sax.XMLReader` |
| `javax.xml.transform.sax.SAXResult`      | `org.xml.sax.ContentHandler`             |
| `javax.xml.transform.stream.StreamSource` | `java.io.File`, `java.io.InputStream`, or `java.io.Reader` |
| `javax.xml.transform.stream.StreamResult` | `java.io.File`, `java.io.OutputStream`, or `java.io.Writer` |

一般使用SOAP协议，本例中默认使用SaajSoapMessageFactory SAAJ 1.3 MessageFactory with SOAP 1.1 Protocol。对于大量的SOAP Message，推荐使用 AxiomSoapMessageFactory。使用方式如下：

```xml
<dependency>
    <groupId>org.apache.ws.commons.axiom</groupId>
    <artifactId>axiom-api</artifactId>
    <version>${axiom.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.ws.commons.axiom</groupId>
    <artifactId>axiom-impl</artifactId>
    <version>${axiom.version}</version>
</dependency>
```

```java
@Bean
public AxiomSoapMessageFactory messageFactory() {
  	AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
    messageFactory.setPayloadCaching(false);
    return messageFactory;
}
```

##### TransportContext

​	默认使用SOAP协议。服务端底层通信使用HttpServletConnection。

```java
TransportContext context = TransportContextHolder.getTransportContext();
HttpServletConnection connection = (HttpServletConnection )context.getConnection();
HttpServletRequest request = connection.getHttpServletRequest();
String ipAddress = request.getRemoteAddr();
```

##### MessageDispatcher

​	分发XML请求到Endpoint。MessageDispatcherServlet封装了MessageDispatcher，按照普通Servlet使用。由此Servlet分布的WSDL命名规则 `http://<server>/<context name>/<@Bean的名称>.wsdl`。

​	注意：若设置 **transformWsdlLocations 为 true**，则访问规则如下：

-  相对路径：`http://<server>/<context name>/<WSDL Bean LocationUri><@Bean的名称>.wsdl`。
-  绝对路径：`<WSDL Bean LocationUri><@Bean的名称>.wsdl`。

#### 3. 服务端

![sequence](/Users/dante/Documents/Project/spring/springboot/springboot-ws-server/sequence.png)

##### 1)  添加依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web-services</artifactId>
</dependency>
<dependency>
  <groupId>wsdl4j</groupId>
  <artifactId>wsdl4j</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ws</groupId>
  <artifactId>spring-ws-test</artifactId>
  <scope>test</scope>
</dependency>
```

##### 2) 创建XSD

```xml-dtd
<!-- 在src/main/resources/schema下 tests.xsd-->
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://org.dante.springboot/ws"
           targetNamespace="http://org.dante.springboot/ws" elementFormDefault="qualified">
	<xs:element name="getTestRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="name" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
    <xs:element name="getTestResponse">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="result" type="xs:dateTime"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>        
</xs:schema>
```

##### 3)  Jaxb2插件生成代码

```xml
<!-- 将 target/generated-sources/jaxb/ 目录设为 Source Folder -->
<build>
  <plugins>
      <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>jaxb2-maven-plugin</artifactId>
          <version>1.6</version>
          <executions>
            <execution>
                <id>xjc</id>
                <goals>
                  <goal>xjc</goal>
                </goals>
            </execution>
          </executions>
          <configuration>
              <packageName>org.dante.springboot.ws</packageName>
              <schemaDirectory>${project.basedir}/src/main/resources/schema/</schemaDirectory>
              <outputDirectory>${project.basedir}/target/generated-sources/jaxb/</outputDirectory>
              <clearOutputDir>false</clearOutputDir>
          </configuration>
      </plugin>
  </plugins>
</build>
```

​	代码生成可以使用命令 `mvn compile` 。

##### 4) 编写 Endpoint

```java
@Slf4j
@Endpoint
public class TestsEndpoint {
	private static final String NAMESPACE_URI = "http://org.dante.springboot/ws";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTestRequest")
	@ResponsePayload
	public GetTestResponse getTest(@RequestPayload GetTestRequest request, SoapHeader header) {
		Iterator<QName> attributes = header.getAllAttributes();
		attributes.forEachRemaining(a -> {
			log.info(a.getLocalPart() + " -- " + a.getNamespaceURI());
		});
		log.info("SoapHeader ==> {}, GetTestRequest ==> {}", header.getName(), request.getName());
		GetTestResponse response = new GetTestResponse();
		response.setResult(dateToXmlDate(Date.from(Instant.now())));
		return response;
	}
}
```

##### 5) 配置

```java
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
  	
  	@Bean(name = "tests")
	public DefaultWsdl11Definition testsWsdl11Definition(XsdSchema testsSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("TestsPort");
		wsdl11Definition.setLocationUri("/xr");
		wsdl11Definition.setTargetNamespace("http://org.dante.springboot/ws");
		wsdl11Definition.setSchema(testsSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema testsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schema/tests.xsd"));
	}
}
```

##### 6) 日志配置

```yaml
logging:
  level:
    root: INFO, stdout
    org.springframework.ws.client.MessageTracing.sent: TRACE
    org.springframework.ws.client.MessageTracing.received: DEBUG
    org.springframework.ws.server.MessageTracing: DEBUG
```

##### 7) 测试

```java
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import javax.xml.transform.Source;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource; 

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootWSServerApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;
	private MockWebServiceClient mockClient;
	@Before
	public void createClient() {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}
	@Test
	public void countrysEndpoint() throws Exception {
		Source requestPayload = new StringSource("<getCountryRequest xmlns='http://org.dante.springboot/ws'>"
				+ "<name>Spain</name>" + "</getCountryRequest>");
		Source responsePayload = new StringSource("<getCountryResponse xmlns='http://org.dante.springboot/ws'>"
				+ "<country>"
					+"<name>Spain</name>"
					+"<population>46704314</population>"
					+"<capital>Madrid</capital>"
					+"<currency>EUR</currency>"
				+"</country>"
				+ "</getCountryResponse>");
	mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
	}
}
```

##### 8) 建议

 - 开发环境使用 dynamic-wsdl，由XSD在运行时创建WSDL。
 - 生产环境使用 static-wsdl。

#### 4. 客户端

##### 1) 添加依赖，同服务端

##### 2) 根据WSDL生成代码

- 添加maven的jaxb2插件

```xml
<plugin>
    <groupId>org.jvnet.jaxb2.maven2</groupId>
    <artifactId>maven-jaxb2-plugin</artifactId>
    <version>0.13.1</version>
    <executions>
        <execution>
          <goals>
              <goal>generate</goal>
          </goals>
        </execution>
    </executions>
    <configuration>
        <schemaLanguage>WSDL</schemaLanguage>
        <generatePackage>com.dante.springboot.ws</generatePackage>
        <generateDirectory>${project.basedir}/target/generated-sources/jaxb</generateDirectory>
        <schemas>
            <schema>
                <fileset>
                    <directory>${basedir}/src/main/resources/wsdl</directory>
                    <includes>
                        <include>*.wsdl</include>
                    </includes>
                </fileset>
            </schema>
        </schemas>
    </configuration>
</plugin>
```

- 将wsdl存到本地（countries.wsdl）

##### 3) 编写Client代码

```java
public class CountryClient extends WebServiceGatewaySupport {
	public GetCountryResponse getCountry(String name) {
		log.info("请求获取国家 {}", name);
		GetCountryRequest request = new GetCountryRequest();
		request.setName(name);
//		GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8080/ws/xc/countries.wsdl", request);
		GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		return response;
	}
}
```

##### 4) 配置客户端

```java
@Configuration
public class WSConfig {
	private final static String DEFAULT_URI = "http://localhost:8080/ws";
	
  	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// 包名必须匹配pom.xml中配置的 <generatePackage>com.dante.springboot.ws</generatePackage>
		marshaller.setContextPath("com.dante.springboot.ws");
		return marshaller;
	}

  	// DefaultUri在配置和具体调用方法，设置一处即可
	@Bean("wsCountryClient")
	public CountryClient wsCountryClient(Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();
		client.setDefaultUri(DEFAULT_URI + "/xc/countries.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
```

#### 5. 安全



### 三. 参考资料

- https://spring.io/guides/gs/producing-web-service/
- https://github.com/spring-projects/spring-ws


- http://blog.csdn.net/WOOSHN/article/details/8145763/