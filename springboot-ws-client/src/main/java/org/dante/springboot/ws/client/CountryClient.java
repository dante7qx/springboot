package org.dante.springboot.ws.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.dante.springboot.ws.GetCountryRequest;
import com.dante.springboot.ws.GetCountryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
