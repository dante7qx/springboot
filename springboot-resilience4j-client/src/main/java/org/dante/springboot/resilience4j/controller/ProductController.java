package org.dante.springboot.resilience4j.controller;

import lombok.RequiredArgsConstructor;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RequestMapping("/client")
@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	/**
	 * 模拟服务调用端 — 并发隔板
	 */
	@GetMapping("/rating_bulkhead/{productId}")
	public ProductDTO getProductBulkhead(@PathVariable int productId) {
		return productService.getProductDtoBulkhead(productId);
	}

	/**
	 * 模拟服务调用端 — 重试
	 */
	@GetMapping("/rating_retry/{productId}")
	public CompletionStage<ProductDTO> getProductRetry(@PathVariable int productId) {
		return productService.getProductDtoRetry(productId);
	}

	/**
	 * 模拟服务调用端 — 超时
	 */
	@GetMapping("/rating_timeout/{productId}")
	public CompletionStage<ProductDTO> getProductTimeout(@PathVariable int productId) {
		return productService.getProductDtoTimeout(productId);
	}

	/**
	 * 模拟服务调用端 — 熔断
	 */
	@GetMapping("/rating_circuit_break/{productId}")
	public ProductDTO getProductCircuitBreak(@PathVariable int productId) {
		return productService.getProductDtoCircuitBreak(productId);
	}

}
