package org.dante.springboot.designpattern.strategy;

import java.util.List;

public interface StockStrategy {
	
	/**
     * 将股票列表排序
     *
     * @param source 源数据
     * @return 排序后的榜单
     */
    List<Stock> sort(List<Stock> source);
	
}
