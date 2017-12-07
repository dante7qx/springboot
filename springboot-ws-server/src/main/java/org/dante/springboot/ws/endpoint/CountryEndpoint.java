package org.dante.springboot.ws.endpoint;

import org.dante.springboot.ws.GetCountryRequest;
import org.dante.springboot.ws.GetCountryResponse;
import org.dante.springboot.ws.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://org.dante.springboot/ws";

	@Autowired
	private CountryRepository countryRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		log.info("GetCountryRequest ==> {}", request.getName());
		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));
		return response;
	}
}
