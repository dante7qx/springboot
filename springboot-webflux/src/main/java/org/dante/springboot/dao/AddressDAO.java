package org.dante.springboot.dao;

import org.dante.springboot.po.AddressPO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface AddressDAO extends ReactiveCrudRepository<AddressPO, String> {
	
	public Mono<AddressPO> findByCode(String code);
	
	public Mono<Long> deleteByCode(String code);
}
