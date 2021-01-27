package org.dante.springboot.resilience4j.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.dante.springboot.resilience4j.dto.ProductRatingDTO;
import org.dante.springboot.resilience4j.dto.ReviewDTO;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
	
	private Map<Integer, ProductRatingDTO> map;
	
	@SuppressWarnings("serial")
	@PostConstruct
    private void init(){

        // product rating 1
		ProductRatingDTO ratingDTO1 = ProductRatingDTO.of(4.5,
				Arrays.asList(ReviewDTO.of("白小纯", 1, 5, "优秀"), ReviewDTO.of("张小凡", 1, 4, "中等"))
        );

        // product rating 2
		ProductRatingDTO ratingDTO2 = ProductRatingDTO.of(4,
				Arrays.asList(ReviewDTO.of("源一", 2, 5, "良好"), ReviewDTO.of("fifty", 2, 3, ""))
        );

        // 用 map 模拟数据库
        this.map = new HashMap<Integer, ProductRatingDTO>() {{
            put(1, ratingDTO1);
            put(2, ratingDTO2);
        }};
    }
	
	/**
	 * 根据 productId 获取商品评价
	 * 
	 * @param productId
	 * @return
	 */
	public ProductRatingDTO getRatingForProduct(int productId) {
        return this.map.getOrDefault(productId, new ProductRatingDTO());
    }


}
