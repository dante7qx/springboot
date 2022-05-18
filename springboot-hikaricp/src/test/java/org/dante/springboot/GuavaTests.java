package org.dante.springboot;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuavaTests {
	
	@Test
	public void tableTest() {
		Table<String, String, Integer> table = HashBasedTable.create();
		table.put("row1", "col1", 10);
		table.put("row1", "col2", 20);
		
		table.put("row2", "col1", 30);
		table.put("row2", "col2", 40);
		
		Integer cellVal = table.get("row1", "col2");
		log.info("row1 -> col2: {}", cellVal);
		
		Set<String> rowKeySet = table.rowKeySet();
		Set<String> columnKeySet = table.columnKeySet();
		Collection<Integer> values = table.values();
		log.info("rowKeySet: {}", rowKeySet);
		log.info("columnKeySet: {}", columnKeySet);
		log.info("values: {}", values);
		
		for (String row : rowKeySet) {
			log.info("Sum: {} -> {}", row, table.row(row).values().stream().mapToInt(Integer::intValue).sum());
			log.info("Reducing: {} -> {}", row, table.row(row).values().stream().collect(Collectors.reducing(0, Integer::intValue, Integer::sum)));
		}
		
		Table<String, String, Integer> table2 = Tables.transpose(table);
		log.info("行转列: {}", table2);
		Set<Cell<String, String, Integer>> cells = table2.cellSet();
		log.info("单元格: {}", cells);
		
		Map<String, Map<String, Integer>> rowMap = table.rowMap();
		Map<String, Map<String, Integer>> colMap = table.columnMap();
		log.info("rowMap: {}", rowMap);
		log.info("colMap: {}", colMap);
	}
	
	
	@Test
	public void concurrencyTester() {
		ConcurrencyTester tester = ThreadUtil.concurrencyTest(100, () -> {
		    // 测试的逻辑内容
		    long delay = RandomUtil.randomLong(100, 1000);
		    ThreadUtil.sleep(delay);
		    Console.log("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
		});
		// 获取总的执行时间，单位毫秒
		Console.log(tester.getInterval());
	}

}
