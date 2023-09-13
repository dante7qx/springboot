package org.dante.springboot.designpattern.strategy.factory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.dante.springboot.designpattern.strategy.Stock;
import org.dante.springboot.designpattern.strategy.StockStrategy;
import org.springframework.stereotype.Service;

/**
 * 高涨幅榜
 */
@Service("HighRise")
public class HighRiseRank implements StockStrategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparingDouble(Stock::getRise).reversed())
                .collect(Collectors.toList());
    }
}
