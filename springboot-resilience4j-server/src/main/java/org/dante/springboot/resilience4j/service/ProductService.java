package org.dante.springboot.resilience4j.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.Strings;
import org.dante.springboot.resilience4j.dto.BaseResponse;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.dante.springboot.resilience4j.dto.ResponseType;
import org.dante.springboot.resilience4j.po.ProductPO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

	private final Map<Integer, ProductPO> productMap = new HashMap<>();

	@PostConstruct
	private void init() {
		this.productMap.put(1, ProductPO.of(1, "曼哈顿E30音箱", 2356.56));
		this.productMap.put(2, ProductPO.of(2, "The Eminem Show", 12.12));
	}

	/**
	 * 根据 productId 获取商品
	 */
	public ProductDTO getProductById(int productId) {
		ProductPO po = this.productMap.get(productId);
		return ProductDTO.of(po.getProductId(), po.getDescription(), po.getPrice(), null);
	}
	
	/**
	 * 获取所有商品
	 */
	public List<ProductDTO> getAllProducts() {
		return this.productMap.values().stream().map(po -> ProductDTO.of(po.getProductId(), po.getDescription(), po.getPrice(),
				ProductRatingDTO.of(0, Collections.emptyList(), ""))).collect(Collectors.toList());
	}
	
	/**
	 * 根据 productId 获取商品 — 模拟流量控制
	 */
	@RateLimiter(name = "productRateLimiter", fallbackMethod = "getProductByIdFallback")
	public BaseResponse<ProductDTO> getProductByIdRateLimiter(int productId) {
		ProductPO po = this.productMap.get(productId);
		ProductDTO productDTO = ProductDTO.of(po.getProductId(), po.getDescription(), po.getPrice(), null);
		return BaseResponse.of(productDTO, ResponseType.SUCCESS, Strings.EMPTY);
	}
	
	@SuppressWarnings("unused")
	private BaseResponse<ProductDTO> getProductByIdFallback(int productId, Throwable throwable) {
		return BaseResponse.of(null, ResponseType.FAILURE, "当前用户较多，请稍后再试。");
	}
}
