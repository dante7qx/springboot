package org.dante.springboot.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookVO implements Serializable {
	
	private static final long serialVersionUID = -3712230614324696962L;
	private Long id;
	private String title;
	private String author;
	private BigDecimal price;
}
