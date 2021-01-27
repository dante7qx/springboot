package org.dante.springboot.resilience4j.service;

import java.util.concurrent.CompletionStage;

import org.dante.springboot.resilience4j.client.ProductClient;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductClient productClient;

	/**
	 * 获取商品信息
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDTO getProduct(int productId) {
		return productClient.getProduct(productId);
	}

	/**
	 * 获取商品 — 重试
	 * 
	 * @param productId
	 * @return
	 */
	public CompletionStage<ProductDTO> getProductDtoRery(Integer productId) {
		ProductDTO product = productClient.getProduct(productId);
		return productClient.getProductRatingDtoRetry(1).thenApply(rating -> {
			return ProductDTO.of(productId, product.getDescription(), product.getPrice(), rating);
		});
	}

	/**
	 * 获取商品 — 超时
	 * 
	 * @param productId
	 * @return
	 */
	public CompletionStage<ProductDTO> getProductDtoTimeout(Integer productId) {
		ProductDTO product = productClient.getProduct(productId);
		return productClient.getProductRatingDtoTimeout(1).thenApply(rating -> {
			return ProductDTO.of(productId, product.getDescription(), product.getPrice(), rating);
		});
	}

	/**
	 * 获取商品 — 并发隔板
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDTO getProductDtoBulkhead(Integer productId) {
		ProductDTO productDTO = productClient.getProduct(productId);
		ProductRatingDTO rating = productClient.getProductRatingDtoBulkhead(productId);
		productDTO.setProductRating(rating);
		return productDTO;
	}

	/**
	 * 获取商品 — 熔断
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDTO getProductDtoCircuitBreak(Integer productId) {
		ProductDTO productDTO = productClient.getProduct(productId);
		ProductRatingDTO rating = productClient.getProductRatingDtoCircuitBreak(productId);
		productDTO.setProductRating(rating);
		return productDTO;
	}

}
