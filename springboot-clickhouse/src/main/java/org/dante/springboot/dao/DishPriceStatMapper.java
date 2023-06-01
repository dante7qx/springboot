package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.DishPriceStatPO;

public interface DishPriceStatMapper {
	
	public List<DishPriceStatPO> selectAvgHistPriceOfDish(Integer year);
	
}
