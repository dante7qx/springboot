package org.dante.springboot.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.dante.springboot.SpringbootDaMengApplicationTests;
import org.dante.springboot.po.Demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;

public class DemoMapperTests extends SpringbootDaMengApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	
	@Test
	void insert() {
		Demo demo = new Demo();
		demo.setDemoName("测试-2");
		demo.setDemoTime(DateUtil.date());
		demo.setDemoContent("<p>你好，达梦！<p>");
		demoMapper.insertDemo(demo);
		Console.log(demo);
		assertTrue(demo.getDemoId() > 0);
	}
	
	@Test
	void update() {
		Demo demo = demoMapper.selectDemoByDemoId(1L);
		demo.setDemoName(demo.getDemoName() + "_U");
		demo.setAttachment("附件");
		demoMapper.updateDemo(demo);
		Console.log(demo);
	}
	
	@Test
	void findList() {
		Console.log(demoMapper.selectDemoList(new Demo()));
	}
	
	@Test
	void findPage() {
		PageHelper.startPage(1, 10);
		List<Demo> demos = demoMapper.selectDemoList(new Demo());
		PageInfo<Demo> page = new PageInfo<>(demos);
		Console.log(page);
	}
	
	@Test
	void findById() {
		Demo demo = demoMapper.selectDemoByDemoId(1L);
		Console.log(demo);
	}
	
	@Test
	void delete() {
		int row = demoMapper.clearDemoData();
		Console.log("删除行 -> {}", row);
		assertTrue(row >= 0);
	}
}
