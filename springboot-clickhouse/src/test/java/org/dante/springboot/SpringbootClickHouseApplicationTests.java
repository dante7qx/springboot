package org.dante.springboot;

import java.util.List;
import java.util.Map;

import org.dante.springboot.dao.DishPriceStatMapper;
import org.dante.springboot.po.DishPriceStatPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.hutool.core.lang.Console;

@SpringBootTest
public class SpringbootClickHouseApplicationTests {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DishPriceStatMapper dishPriceStatMapper;
	
	/**
	 * 菜品的平均历史价格
	 */
	@Test
	public void findAvgHistPriceOfDish() {
		String sql = """
				select
				    round(touint32orzero(extract(menu_date, '^\\d{4}')), -1) as d,
				    count(),
				    round(avg(price), 2),
				    bar(avg(price), 0, 100, 100)
				from menu_item_denorm
				where (menu_currency = 'dollars') and (d > 0) and (d < 2022)
				group by d
				order by d asc;
				""";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		Console.log(results);
	}
	
	@Test
	public void selectAvgHistPriceOfDish() {
		int year = 2000;
		List<DishPriceStatPO> results = dishPriceStatMapper.selectAvgHistPriceOfDish(year);
		Console.log(results);
	}
	
}
