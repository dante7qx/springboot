package org.dante.springboot.controller;

import org.dante.springboot.po.AddressPO;
import org.dante.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping("")
	public Mono<AddressPO> updateAddress(@RequestBody AddressPO address) {
		return addressService.persistAddress(address);
	}
	
	@DeleteMapping("/{code}")
	public Mono<Long> deleteByCode(@PathVariable String code) {
		return addressService.deleteByCode(code);
	}
	
	@GetMapping("/{code}")
	public Mono<AddressPO> queryByCode(@PathVariable String code) {
		return addressService.findByCode(code);
	}
	
//	@GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@GetMapping(value = "")
	public Flux<AddressPO> queryAll() {
		return addressService.findAll();
	}
	
}
