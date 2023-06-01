package org.dante.springboot.po;

import lombok.Data;

/**
 * 菜品价格统计
 * 
 * @author dante
 *
 */
@Data
public class DishPriceStatPO {
	/** 年份 */
	private String year;
	/** 菜品数量 */
	private Long count;
	/** 年平均价格 */
	private Double avgPrice;
	/** 条形图 */
	private String barChart;
	
}
