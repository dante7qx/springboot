package org.dante.springboot.springevent;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductNotifyService {
	
	public void notify(Product product) {
		log.info("通知服务 -> {}", product);
	}

}
