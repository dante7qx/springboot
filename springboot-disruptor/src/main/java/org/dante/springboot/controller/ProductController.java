package org.dante.springboot.controller;

import org.dante.springboot.springevent.Product;
import org.dante.springboot.springevent.Product2;
import org.dante.springboot.springevent.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参考资料：https://developer.aliyun.com/article/829271
 * 
 * @author dante
 *
 */
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) {
		productService.saveProduct(product);
		return product;
	}
	
	@PostMapping("/addProduct2")
	public Product2 addProduct2(@RequestBody Product2 product) {
		productService.saveProduct2(product);
		return product;
	}

}
