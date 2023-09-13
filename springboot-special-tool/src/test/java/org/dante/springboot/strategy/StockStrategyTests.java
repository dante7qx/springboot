package org.dante.springboot.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.dante.springboot.SpringbootSpecialToolApplicationTests;
import org.dante.springboot.designpattern.strategy.Stock;
import org.dante.springboot.designpattern.strategy.enums.RankServiceEnumImpl;
import org.dante.springboot.designpattern.strategy.factory.RankServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.lang.Console;

class StockStrategyTests extends SpringbootSpecialToolApplicationTests {

	@Autowired
	private RankServiceImpl rankService;
	@Autowired
	private RankServiceEnumImpl rankServiceEnum;
	
	
	/**
	 * 股票涨幅趋势
	 * rankType	HighRise、HighPrice、LowPrice
	 */
	@Test
	void stockRank() {
		List<Stock> highRiseStock = rankService.getRank("HighRise");
		Console.log("HighRise => {}", highRiseStock);
		
		List<Stock> highPriceStock = rankService.getRank("HighPrice");
		Console.log("HighPrice => {}", highPriceStock);
		
		List<Stock> lowPriceStock = rankService.getRank("LowPrice");
		Console.log("LowPrice => {}", lowPriceStock);
		
		assertEquals(3, lowPriceStock.size());
	}
	
	/**
	 * 股票涨幅趋势
	 * rankType	HighRise、HighPrice、LowPrice
	 */
	@Test
	void stockRankEnum() {
		List<Stock> highRiseStock = rankServiceEnum.getRank("HighRise");
		Console.log("HighRise => {}", highRiseStock);
		
		List<Stock> highPriceStock = rankServiceEnum.getRank("HighPrice");
		Console.log("HighPrice => {}", highPriceStock);
		
		List<Stock> lowPriceStock = rankServiceEnum.getRank("LowPrice");
		Console.log("LowPrice => {}", lowPriceStock);
		
		assertEquals(3, lowPriceStock.size());
	}
}
