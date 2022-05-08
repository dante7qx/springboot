package org.dante.springboot.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.SpringbootHikariCPApplicationTests;
import org.dante.springboot.po.MultiThreadInsertPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiThreadInsertDAOTests extends SpringbootHikariCPApplicationTests {

	@Autowired
	private MultiThreadInsertDAO multiThreadInsertDAO;

	private static int dataSize = 10000;
	private static List<MultiThreadInsertPO> list = new LinkedList<>();

	@BeforeEach
	public void init() {
		for (int i = 0; i < dataSize; i++) {
			MultiThreadInsertPO po = new MultiThreadInsertPO();
			po.setUid(IdUtil.nanoId(32));
			po.setName("测试数据【" + i + "】");
			po.setCreateTime(DateUtil.date());
			po.setUpdateTime(DateUtil.date());
			list.add(po);
		}
		log.info("*******************************数据初始化完成*******************************");
	}

	@Test
	public void batchInsert() {
		StopWatch stopWatch = new StopWatch("多线程批量导入【" + dataSize + "】条数据");
		stopWatch.start();
		
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
		List<Future<Integer>> futures = new ArrayList<Future<Integer>>(nThreads);

		for (int i = 0; i < nThreads; i++) {
			final List<MultiThreadInsertPO> groupList = list.subList(dataSize / nThreads * i, dataSize / nThreads * (i + 1));
			Callable<Integer> task = () -> {
				multiThreadInsertDAO.saveAll(groupList);
				return 1;
			};
			futures.add(executorService.submit(task));
		}
		executorService.shutdown();
		if (!CollectionUtils.isEmpty(futures)) {  
    		futures.forEach(f -> {
    			for(;;) { 
    				try {
	    				if(f.isDone()) {
	    					f.get();
	    					break; 
	    				}
	    				TimeUnit.MILLISECONDS.sleep(2L);
    				} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
    			}
    		});
	    }
		
		stopWatch.stop();
		// 打印出耗时
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
	
	public void singleCRUD() {
		StopWatch stopWatch = new StopWatch("单体数据的CRUD");
		stopWatch.start();
		MultiThreadInsertPO po = new MultiThreadInsertPO();
		po.setUid(IdUtil.nanoId(32));
		po.setName("测试数据");
		po.setCreateTime(DateUtil.date());
		po.setUpdateTime(DateUtil.date());
		MultiThreadInsertPO savedPO = multiThreadInsertDAO.save(po);
		log.info("MultiThreadInsertPO -> {}", savedPO);
		multiThreadInsertDAO.deleteById(savedPO.getId());
		stopWatch.stop();
		// 打印出耗时
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
	
}
