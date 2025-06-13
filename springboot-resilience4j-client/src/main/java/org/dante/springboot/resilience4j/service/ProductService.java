package org.dante.springboot.resilience4j.service;

import lombok.RequiredArgsConstructor;
import org.dante.springboot.resilience4j.client.ProductClient;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionStage;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductClient productClient;

	/**
	 * 获取商品信息
	 */
	public ProductDTO getProduct(int productId) {
		return productClient.getProduct(productId);
	}

	/**
	 * 获取商品 — 重试
	 * 
	 */
	public CompletionStage<ProductDTO> getProductDtoRetry(Integer productId) {
		ProductDTO product = productClient.getProduct(productId);
		return productClient.getProductRatingDtoRetry(1).thenApply(rating -> {
			return ProductDTO.of(productId, product.getDescription(), product.getPrice(), rating);
		});
	}

	/**
	 * 获取商品 — 超时
	 */
	public CompletionStage<ProductDTO> getProductDtoTimeout(Integer productId) {
		ProductDTO product = productClient.getProduct(productId);
		return productClient.getProductRatingDtoTimeout(1).thenApply(rating -> {
			return ProductDTO.of(productId, product.getDescription(), product.getPrice(), rating);
		});
	}

	/**
	 * 获取商品 — 并发隔板
	 */
	public ProductDTO getProductDtoBulkhead(Integer productId) {
		ProductDTO productDTO = productClient.getProduct(productId);
		ProductRatingDTO rating = productClient.getProductRatingDtoBulkhead(productId);
		productDTO.setProductRating(rating);
		return productDTO;
	}

	/**
	 * 获取商品 — 熔断
	 */
	public ProductDTO getProductDtoCircuitBreak(Integer productId) {
		ProductDTO productDTO = productClient.getProduct(productId);
		ProductRatingDTO rating = productClient.getProductRatingDtoCircuitBreak(productId);
		productDTO.setProductRating(rating);
		return productDTO;
	}

}
