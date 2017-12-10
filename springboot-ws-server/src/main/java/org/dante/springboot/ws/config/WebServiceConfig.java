package org.dante.springboot.ws.config;

import java.util.List;

import org.dante.springboot.ws.interceptor.WsInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		// 将 wsdl 的 locationUrl 做为转换地址
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
	@Bean
	public SoapFaultAnnotationExceptionResolver soapFaultAnnotationExceptionResolver() {
		SoapFaultAnnotationExceptionResolver resolver = new SoapFaultAnnotationExceptionResolver();
		return resolver;
	}
	
	@Bean
	Wss4jSecurityInterceptor wss4jSecurityInterceptor() {
		Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
		interceptor.setSecurementActions("UsernameToken");
		interceptor.setSecurementUsername("dante");
		interceptor.setSecurementPassword("123456");
		interceptor.setSecurementPasswordType("PasswordText");
		interceptor.setSecurementUsernameTokenCreated(true);
		interceptor.setSecurementUsernameTokenNonce(true);
		return interceptor;
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(new WsInterceptor());
		// 安全认证还未研究明白
//		interceptors.add(wss4jSecurityInterceptor());
	}	

	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/xc");
		wsdl11Definition.setTargetNamespace("http://org.dante.springboot/ws");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("schema/countries.xsd"));
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
	
	/*
	@Bean
	public AxiomSoapMessageFactory messageFactory() {
		AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
		// false, 直接从socket stream读取SOAP body。默认为true
		messageFactory.setPayloadCaching(false);
		messageFactory.setSoapVersion(SoapVersion.SOAP_11);
		return messageFactory;
	}
	*/
	
}