package org.dante.springboot;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;

import cn.hutool.core.lang.Console;

@SpringBootTest
public class SpringbootMybatisFlexApplicationTests {


	/** 
	 * 不支持数据脱敏
	 */
	@Test
	public void testMultiDatasource() {
		List<Row> rows = Db.selectAll("tb_account");
		Console.log(rows);
		
		Console.log("=================================");
		
		List<Row> row2s =  DataSourceKey.use("ds2"
			    , () -> Db.selectAll("tb_account"));
		Console.log(row2s);
	}
}
