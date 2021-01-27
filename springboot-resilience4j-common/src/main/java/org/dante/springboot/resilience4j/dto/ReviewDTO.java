package org.dante.springboot.resilience4j.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评分DTO
 * 
 * @author dante
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ReviewDTO {
	private String name;	// 评论人
	private int productId;	// 商品Id
    private int rating;		// 评分
    private String comment;	// 评论
}
