package org.dante.springboot.service;

import org.dante.springboot.dao.AddressDAO;
import org.dante.springboot.po.AddressPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressService {

	@Autowired
	private AddressDAO addressDAO;

	/**
	 * 保存 Address，已存在 Code 的 Address，去更新 
	 * 
	 * @param address
	 * @return
	 */
	public Mono<AddressPO> persistAddress(AddressPO address) {
		return addressDAO.save(address)
				.onErrorResume(e -> addressDAO.findByCode(address.getCode())
						.flatMap(o -> {
							address.setId(o.getId());
							return addressDAO.save(address);
		}));
	}

	public Mono<AddressPO> findByCode(String code) {
		return addressDAO.findByCode(code);
	}
	
	public Mono<Long> deleteByCode(String code) {
		return addressDAO.deleteByCode(code);
	}
	
	public Flux<AddressPO> findAll() {
		return addressDAO.findAll();
	}
	
}
