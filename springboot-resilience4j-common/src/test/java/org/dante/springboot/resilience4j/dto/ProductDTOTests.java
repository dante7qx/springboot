package org.dante.springboot.resilience4j.dto;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductDTOTests {

	@Test
	public void buildProduct() {
		ProductDTO dto = ProductDTO.of(1, "陶瓷杯", 198, ProductRatingDTO.of(0, Collections.emptyList()));
		Assertions.assertEquals(1, dto.getProductId());
	}
	
}
