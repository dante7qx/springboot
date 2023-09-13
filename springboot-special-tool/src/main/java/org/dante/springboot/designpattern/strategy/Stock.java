package org.dante.springboot.designpattern.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	// 股票交易代码
	private String code;

	// 现价
	private Double price;

	// 涨幅
	private Double rise;
}
