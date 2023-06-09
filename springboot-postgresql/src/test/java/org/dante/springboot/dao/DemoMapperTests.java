package org.dante.springboot.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.SpringbootPostgreSQLApplicationTests;
import org.dante.springboot.po.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;

public class DemoMapperTests extends SpringbootPostgreSQLApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	
	@Test
	public void insert() {
		Demo demo = new Demo();
		demo.setDemoName("单条测试");
		demo.setDemoTime(DateUtil.date());
		demo.setCreateBy("dante");
		demo.setCreateTime(DateUtil.date());
		demoMapper.insertDemo(demo);
		Console.log(demo);
		assertTrue(demo.getDemoId() > 0);
	}
	
	@Test
	public void batchInsert() {
		int dataSize = 100000;
		int batchSize = 3000;
		List<Demo> demos = Lists.newArrayList();
		for (int i = 0; i < dataSize; i++) {
			Demo demo = new Demo();
			demo.setDemoName("测试数据" + i);
			demo.setDemoTime(DateUtil.date());
			demo.setCreateBy("dante");
			demo.setCreateTime(DateUtil.date());
			demos.add(demo);
		}
		StopWatch stopWatch = new StopWatch("MyBatis循环导入【" + dataSize + "】条数据");
		stopWatch.start();
		if(dataSize < batchSize) {
			demoMapper.batchInsertDemo(demos);
			demos = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
			return;
		} 
		int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
		for (int i = 0; i < count; i++) {
			int fromIndex = i * batchSize; //计算每批的起始索引
		    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
		    List<Demo> groupList = demos.subList(fromIndex, toIndex); //获取子列表
			demoMapper.batchInsertDemo(groupList);
			groupList = null;
		}
		demos = null;
		stopWatch.stop();
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}

}
