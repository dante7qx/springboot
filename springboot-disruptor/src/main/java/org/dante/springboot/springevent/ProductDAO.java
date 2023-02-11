package org.dante.springboot.springevent;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductDAO {

	public void saveProduct(Product product) {
		log.info("业务保存 --> {}", product);
	}
}
