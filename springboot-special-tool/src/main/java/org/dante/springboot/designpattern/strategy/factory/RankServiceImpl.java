package org.dante.springboot.designpattern.strategy.factory;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dante.springboot.designpattern.strategy.Stock;
import org.dante.springboot.designpattern.strategy.StockStrategy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class RankServiceImpl {

	List<Stock> source = Lists.newArrayList(new Stock("000333", 20D, 10D), new Stock("608271", 10D, 3D), new Stock("330292", 5D, 5D));
	
	/**
     * 利用注解@Resource和@Autowired特性,直接获取所有策略类
     * key = @Service的value
     */
    @Resource
    private Map<String, StockStrategy> rankMap;
    
    /**
     * 前端传入榜单类型, 返回排序完的榜单
     *
     * @param rankType 榜单类型 和Service注解的value属性一致
     * @return 榜单数据
     */
    public List<Stock> getRank(String rankType) {
        // 判断策略是否存在
        if (!rankMap.containsKey(rankType)) {
            throw new IllegalArgumentException("rankType not found");
        }
        // 获得策略实例
        StockStrategy rank = rankMap.get(rankType);
        // 执行策略
        return rank.sort(source);
    }

}
