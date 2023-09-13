package org.dante.springboot.designpattern.strategy.factory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.dante.springboot.designpattern.strategy.Stock;
import org.dante.springboot.designpattern.strategy.StockStrategy;
import org.springframework.stereotype.Service;

/**
 * 高价榜
 * 注意申明 Service.value = HighPrice,他是我们的key,下同
 */
@Service("HighPrice")
public class HighPriceRank implements StockStrategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparingDouble(Stock::getPrice).reversed())
                .collect(Collectors.toList());
    }
}
