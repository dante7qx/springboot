package org.dante.springboot.designpattern.strategy.factory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.dante.springboot.designpattern.strategy.Stock;
import org.dante.springboot.designpattern.strategy.StockStrategy;
import org.springframework.stereotype.Service;

/**
 * 低价榜
 */
@Service("LowPrice")
public class LowPriceRank implements StockStrategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice))
                .collect(Collectors.toList());
    }
}
