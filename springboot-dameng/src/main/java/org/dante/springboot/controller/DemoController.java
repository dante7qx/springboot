package org.dante.springboot.controller;

import java.util.LinkedList;
import java.util.List;

import org.dante.springboot.dao.DemoMapper;
import org.dante.springboot.po.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;

@RestController
public class DemoController {
	
	@Autowired
	private DemoMapper demoMapper;
	
	@GetMapping("/")
	public String hello() {
		return "你好";
	}
	
	@GetMapping("/page")
	public PageInfo<Demo> findPage() {
		PageHelper.startPage(1, 10);
		List<Demo> demos = demoMapper.selectDemoList(new Demo());
		return  new PageInfo<>(demos);
	}
	
	@GetMapping("/batch")
	public Integer batchInsert() {
		List<Demo> list = new LinkedList<>();
		for (int i = 0; i < 100000; i++) {
			Demo po = new Demo();
			po.setDemoName("测试数据【" + i + "】");
			po.setDemoContent("<p>你好，达梦！<p>");
			po.setDemoTime(DateUtil.date());
			po.setCreateBy("系统批量插入");
			po.setCreateTime(DateUtil.date());
			list.add(po);
		};
		demoMapper.batchInsertDemo(list);
		return demoMapper.selectDemoCount();
	}
	
}
