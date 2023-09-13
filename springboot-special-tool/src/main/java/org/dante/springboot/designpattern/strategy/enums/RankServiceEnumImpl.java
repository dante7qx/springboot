package org.dante.springboot.designpattern.strategy.enums;

import java.util.List;

import org.dante.springboot.designpattern.strategy.Stock;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class RankServiceEnumImpl {

	List<Stock> source = Lists.newArrayList(new Stock("000333", 20D, 10D), new Stock("608271", 10D, 3D), new Stock("330292", 5D, 5D));
	
    /**
     * 前端传入榜单类型, 返回排序完的榜单
     *
     * @param rankType 榜单类型 和Service注解的value属性一致
     * @return 榜单数据
     */
    public List<Stock> getRank(String rankType) {
    	// 获取策略，这里如果未匹配会抛 IllegalArgumentException异常
        StockRankEnum rank = StockRankEnum.valueOf(rankType);
        // 然后执行策略
        return rank.sort(source);
    }

}
