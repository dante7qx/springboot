package org.dante.springboot.ws.config;

import java.util.List;
import java.util.Properties;

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
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
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
	public Wss4jSecurityInterceptor wss4jSecurityInterceptor() {
		Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationActions("Timestamp UsernameToken");
        securityInterceptor.setSecurementTimeToLive(3600);
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
        return securityInterceptor;
	}
	
	@Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler(){
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("dante", "123456");
        callbackHandler.setUsers(users);
        return callbackHandler;
    }
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(new WsInterceptor());
		interceptors.add(wss4jSecurityInterceptor());
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