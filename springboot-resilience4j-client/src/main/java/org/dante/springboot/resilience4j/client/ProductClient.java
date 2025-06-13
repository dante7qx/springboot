package org.dante.springboot.resilience4j.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dante.springboot.http.client.JsonHttpClient;
import org.dante.springboot.resilience4j.dto.ProductDTO;
import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClient {

	private final JsonHttpClient httpClient;

	@Value("${product.service.endpoint}")
	private String productEndpoint;

	private static int retryCount; // 记录重试次数，进行验证


	/**
	 * 获取商品信息
	 */
	@SneakyThrows
    public ProductDTO getProduct(int productId) {
		return httpClient.get(this.productEndpoint + "/" + productId, ProductDTO.class);
	}
	
	/**
	 * 服务端模拟随机失败，客户端实现超时机制
	 */
	@TimeLimiter(name = "ratingTimeoutService", fallbackMethod = "getDefaultTimeout")
	public CompletionStage<ProductRatingDTO> getProductRatingDtoTimeout(int productId) {
		log.info("[超时模拟]，开始调用 {}", Instant.now());
		Supplier<ProductRatingDTO> supplier = () -> {
			try {
				String url = this.productEndpoint + "/rating_timeout/" + productId;
				return httpClient.get(url, ProductRatingDTO.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return CompletableFuture.supplyAsync(supplier);
	}

	/**
	 * 服务端模拟随机失败，客户端实现重试
	 * 
	 */
	@Retry(name = "ratingRetryService", fallbackMethod = "getDefaultProductRating")
	public CompletionStage<ProductRatingDTO> getProductRatingDtoRetry(int productId) {
		retryCount++;
		log.info("[重试模拟 {}]，开始调用 {}", retryCount, Instant.now());
		Supplier<ProductRatingDTO> supplier = () -> {
			try {
				String url = this.productEndpoint + "/rating_random_fail/" + productId;
				return httpClient.get(url, ProductRatingDTO.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return CompletableFuture.supplyAsync(supplier);
	}

	/**
	 * 服务端模拟响应缓慢，客户端设置并发隔板
	 */
	@SneakyThrows
    @Bulkhead(name = "ratingBulkheadService", type = Type.SEMAPHORE, fallbackMethod = "getDefault")
	public ProductRatingDTO getProductRatingDtoBulkhead(int productId) {
		log.info("[并发隔板 {}]，调用开始。", Instant.now());
		ProductRatingDTO productRatingDTO = httpClient.get(this.productEndpoint + "/rating_slow_response/" + productId, ProductRatingDTO.class);
		log.info("[并发隔板 {}]，调用结束。", Instant.now());
		return productRatingDTO;
	}
	
	/**
	 * 服务端模拟响应延迟、响应失败，客户端设置熔断机制
	 */
	@SneakyThrows
    @Retry(name = "ratingCircuitBreakService", fallbackMethod = "getDefault")
    @CircuitBreaker(name = "ratingCircuitBreakService", fallbackMethod = "getDefault")
	public ProductRatingDTO getProductRatingDtoCircuitBreak(int productId) {
		log.info("[熔断 {}]，调用开始。", Instant.now());
		ProductRatingDTO productRatingDTO = httpClient.get(this.productEndpoint + "/rating_circuit_break/" + productId, ProductRatingDTO.class);
		log.info("[熔断 {}]，调用结束。", Instant.now());
		return productRatingDTO;
	}

	/**
	 * 客户端失败回调方法
	 */
	@SuppressWarnings("unused")
	private ProductRatingDTO getDefault(int productId, Throwable throwable) {
		log.info("==> 进入回调方法.");
		return ProductRatingDTO.of(0, Collections.emptyList(), "客户端失败回调");
	}
	
	/**
	 * 客户端超时回调方法
	 */
	@SuppressWarnings("unused")
	private CompletionStage<ProductRatingDTO> getDefaultTimeout(int productId, Throwable throwable){
		log.info("[超时模拟 {} ]，进入回调方法.");
        return CompletableFuture.supplyAsync(() -> ProductRatingDTO.of(0, Collections.emptyList(), "客户端超时回调"));
    }

	/**
	 * 客户端重试回调方法
	 */
	@SuppressWarnings("unused")
	private CompletionStage<ProductRatingDTO> getDefaultProductRating(int productId,
			HttpClientErrorException throwable) {
		retryCount = 0;
		log.info("[重试模拟 {} ]，进入回调方法.", retryCount);
		return CompletableFuture.supplyAsync(() -> ProductRatingDTO.of(0, Collections.emptyList(), "客户端重试回调"));
	}
}
