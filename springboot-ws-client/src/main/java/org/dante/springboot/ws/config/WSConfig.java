package org.dante.springboot.ws.config;

import org.dante.springboot.ws.client.CountryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
public class WSConfig {

	private final static String DEFAULT_URI = "http://localhost:8080/ws";

	@Bean
	public Wss4jSecurityInterceptor securityInterceptor() {
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
		wss4jSecurityInterceptor.setSecurementUsername("dante");
		wss4jSecurityInterceptor.setSecurementPassword("123456");
		return wss4jSecurityInterceptor;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// 包名必须匹配pom.xml中配置的 <generatePackage>com.dante.springboot.ws</generatePackage>
		marshaller.setContextPath("com.dante.springboot.ws");
		return marshaller;
	}

	@Bean("wsCountryClient")
	public CountryClient wsCountryClient(Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();
		// DefaultUri在配置和具体调用方法，设置一处即可
		client.setDefaultUri(DEFAULT_URI + "/xc/countries.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		ClientInterceptor[] interceptors = new ClientInterceptor[] {securityInterceptor()};
		client.setInterceptors(interceptors);
		return client;
	}

}
