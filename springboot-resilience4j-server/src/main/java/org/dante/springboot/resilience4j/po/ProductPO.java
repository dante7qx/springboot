package org.dante.springboot.resilience4j.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品PO
 * 
 * @author dante
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProductPO {
	private int productId;
    private String description;
    private double price;
}
