package org.dante.springboot.resilience4j.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品评分DTO
 * 
 * @author dante
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProductRatingDTO {
	private double avgRating;			// 平均评分
    private List<ReviewDTO> reviews;	// 评分列表
}
