package org.dante.springboot.designpattern.strategy;

import java.util.List;

import org.dante.springboot.designpattern.strategy.factory.RankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
	
	@Autowired
	private RankServiceImpl rankService;
	
	/**
	 * 股票涨幅趋势
	 * 
	 * @param rankType	HighRise、HighPrice、LowPrice
	 * @return
	 */
	@GetMapping("/{rankType}")
	public List<Stock> stockRank(@PathVariable String rankType) {
		return rankService.getRank(rankType);
	}
	
}
