package org.dante.springboot.resilience4j.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品DTO
 * 
 * @author dante
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProductDTO {
	private int productId;					// 商品Id
    private String description;				// 商品描述
    private double price;					// 商品价格
    private ProductRatingDTO productRating;	// 商品评分

}
