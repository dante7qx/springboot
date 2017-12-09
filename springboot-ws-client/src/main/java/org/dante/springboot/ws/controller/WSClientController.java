package org.dante.springboot.ws.controller;

import org.dante.springboot.ws.client.CountryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dante.springboot.ws.Country;
import com.dante.springboot.ws.GetCountryResponse;

@RestController
public class WSClientController {
	
	@Autowired
	private CountryClient wsCountryClient;

	@GetMapping("/country")
	public Object getCountry(String name) {
		GetCountryResponse resp = wsCountryClient.getCountry(name);
		Country country = resp.getCountry();
		return country;
	}
	
	
}
