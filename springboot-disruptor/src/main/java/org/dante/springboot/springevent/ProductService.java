package org.dante.springboot.springevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	@Autowired
	private BizPublisher<Product> bizPublisher;
	
	@Autowired
	private ProductDAO productDAO;

	public void saveProduct(Product product) {
		// 业务处理
		productDAO.saveProduct(product);
		// 事件发布
		bizPublisher.publish(product);
		
		log.info("业务完成 --> {}", product);
	}

}
