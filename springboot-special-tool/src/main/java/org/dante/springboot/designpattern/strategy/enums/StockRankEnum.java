package org.dante.springboot.designpattern.strategy.enums;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.dante.springboot.designpattern.strategy.Stock;

/**
 * 枚举策略类
 * 
 * @author dante
 *
 */
public enum StockRankEnum {
	
	HighPrice {
        @Override
        public List<Stock> sort(List<Stock> source) {
            return source.stream()
                    .sorted(Comparator.comparingDouble(Stock::getPrice).reversed())
                    .collect(Collectors.toList());
        }
    },
    LowPrice {
        @Override
        public List<Stock> sort(List<Stock> source) {
            return source.stream()
                    .sorted(Comparator.comparingDouble(Stock::getPrice))
                    .collect(Collectors.toList());
        }
    },
    HighRise {
        @Override
        public List<Stock> sort(List<Stock> source) {
            return source.stream()
                    .sorted(Comparator.comparingDouble(Stock::getRise).reversed())
                    .collect(Collectors.toList());
        }
    };

    // 这里定义了策略接口
    public abstract List<Stock> sort(List<Stock> source);
	
}
