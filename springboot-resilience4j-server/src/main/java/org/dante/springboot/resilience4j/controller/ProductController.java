package org.dante.springboot.resilience4j.controller;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.resilience4j.dto.BaseResponse;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.dante.springboot.resilience4j.service.ProductService;
import org.dante.springboot.resilience4j.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private RatingService ratingService;
	
	@GetMapping("/{productId}")
    public ProductDTO getProduct(@PathVariable int productId){
        return productService.getProductById(productId);
    }
	
	@GetMapping("/rating/{productId}")
	public ProductRatingDTO getRating(@PathVariable Integer productId) {
		return ratingService.getRatingForProduct(productId);
	}
	
	/**
	 * 服务端并发压力大 — 设置流控
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rate_limiter/{productId}")
	public BaseResponse<ProductDTO> getProductRateLimiter(@PathVariable Integer productId) {
		return productService.getProductByIdRateLimiter(productId);
	}
	
	/**
	 * 服务提供者 — 重试模拟
	 * 
	 * @param productId
	 * @return
	 */
	@GetMapping("/rating_random_fail/{productId}")
	public ResponseEntity<ProductRatingDTO> getRatingRandomFail(@PathVariable Integer productId) {
		ProductRatingDTO productRatingDTO = ratingService.getRatingForProduct(productId);
		return retryFailRandomly(productRatingDTO);
	}
	
	/**
	 * 服务提供者 — 重试服务端网络抖动
	 * 
	 * @param productId
	 * @return
	 * @throws InterruptedException 
	 */
	@GetMapping("/rating_timeout/{productId}")
	public ResponseEntity<ProductRatingDTO> getRatingTimeout(@PathVariable Integer productId) throws InterruptedException {
		int second = ThreadLocalRandom.current().nextInt(1, 5);
		log.info("[服务端模拟超时场景，超时 {} 秒]", second);
		TimeUnit.SECONDS.sleep(second);
		return ResponseEntity.ok(ratingService.getRatingForProduct(productId));
	}
	
	/**
	 * 服务提供者 — 模拟服务端处理缓慢
	 * 
	 * @param productId
	 * @return
	 * @throws InterruptedException 
	 */
	@GetMapping("/rating_slow_response/{productId}")
	public ResponseEntity<ProductRatingDTO> getRatingSlowResponse(@PathVariable Integer productId) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10L);
		return ResponseEntity.ok(ratingService.getRatingForProduct(productId));
	}
	
	/**
	 * 模拟服务随机失败
	 * 
	 * @param productRatingDTO
	 * @return
	 */
	private ResponseEntity<ProductRatingDTO> retryFailRandomly(ProductRatingDTO productRatingDTO){
        int random = ThreadLocalRandom.current().nextInt(1, 4);
        log.info("[服务端模拟重试场景，数字] -> {}", random);
        if(random < 2){
            return ResponseEntity.status(500).build();
        }else if(random < 3){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productRatingDTO);
    }
	
	/**
	 * 服务提供者 — 模拟熔断场景
	 * 
	 * @param productId
	 * @return
	 * @throws InterruptedException 
	 */
	@GetMapping("/rating_circuit_break/{productId}")
	public ResponseEntity<ProductRatingDTO> getRatingCircuitBreakResponse(@PathVariable Integer productId) throws InterruptedException {
		ProductRatingDTO productRatingDTO = ratingService.getRatingForProduct(productId);
		return circuitBreakFailRandomly(productRatingDTO);
	}
	
	/**
	 * 模拟熔断场景
	 * 
	 * @param productRatingDto
	 * @return
	 * @throws InterruptedException
	 */
	private ResponseEntity<ProductRatingDTO> circuitBreakFailRandomly(ProductRatingDTO productRatingDto) throws InterruptedException {
        // 模拟响应延迟
		TimeUnit.MILLISECONDS.sleep(100L);
        // 模拟响应失败
        int random = ThreadLocalRandom.current().nextInt(1, 4);
        if(random < 3) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(productRatingDto);
    }
	
}
