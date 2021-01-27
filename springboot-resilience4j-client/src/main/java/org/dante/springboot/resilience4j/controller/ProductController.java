package org.dante.springboot.resilience4j.controller;

import java.util.concurrent.CompletionStage;

import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/client")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 模拟服务调用端 — 并发隔板
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rating_bulkhead/{productId}")
	public ProductDTO getProductBulkhead(@PathVariable int productId) {
		return productService.getProductDtoBulkhead(productId);
	}

	/**
	 * 模拟服务调用端 — 重试
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rating_retry/{productId}")
	public CompletionStage<ProductDTO> getProductRery(@PathVariable int productId) {
		return productService.getProductDtoRery(productId);
	}

	/**
	 * 模拟服务调用端 — 超时
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rating_timeout/{productId}")
	public CompletionStage<ProductDTO> getProductTimeout(@PathVariable int productId) {
		return productService.getProductDtoTimeout(productId);
	}

	/**
	 * 模拟服务调用端 — 熔断
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rating_circuit_break/{productId}")
	public ProductDTO getProductCircuitBreak(@PathVariable int productId) {
		return productService.getProductDtoCircuitBreak(productId);
	}

}
