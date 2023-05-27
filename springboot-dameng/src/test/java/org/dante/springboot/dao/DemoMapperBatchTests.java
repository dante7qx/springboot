package org.dante.springboot.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dante.springboot.SpringbootDaMengApplicationTests;
import org.dante.springboot.po.Demo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;

public class DemoMapperBatchTests extends SpringbootDaMengApplicationTests {

	@Autowired
	private DemoMapper demoMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	// 一万数据，单次批量2000
//		private static int dataSize = 10000;		// 数据集数量
//		private static int batchSize = 3000;	    // 单次批处理数量
		
		// 十万数据，单次批量10000
		private static int dataSize = 100000;		// 数据集数量
		private static int batchSize = 5000;	    // 单次批处理数量
		
		// 百万数据，单次批量10000
//		private static int dataSize = 1000000;		// 数据集数量
//		private static int batchSize = 10000;	    // 单次批处理数量
		
		// 千万数据，单次批量100000
//		private static int dataSize = 10000000;		// 数据集数量
//		private static int batchSize = 3000;	    // 单次批处理数量
		
		
		private static List<Demo> list = new LinkedList<>();
	
		@BeforeEach
		public void init() {
			for (int i = 0; i < dataSize; i++) {
				Demo po = new Demo();
				po.setDemoName("测试数据【" + i + "】");
				po.setDemoContent("<p>你好，达梦！<p>");
				po.setDemoTime(DateUtil.date());
				po.setCreateBy("系统批量插入");
				po.setCreateTime(DateUtil.date());
				list.add(po);
			}
			Console.log("*******************************数据初始化完成*******************************");
		}
		
		/**
		 * ExecutorType.BATCH 的插入方式
		 */
		@Test
		public void executorTypeBatch() {
			StopWatch stopWatch = new StopWatch("MyBatis ExecutorType.BATCH 导入【" + dataSize + "】条数据");
			stopWatch.start();
			if(dataSize < batchSize) {
				demoMapper.batchInsertDemo(list);
				list = null;
				stopWatch.stop();
				Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
				return;
			} 
			try {
				SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
				int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
				for (int i = 0; i < count; i++) {
					int fromIndex = i * batchSize; //计算每批的起始索引
				    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
				    List<Demo> groupList = list.subList(fromIndex, toIndex); //获取子列表
				    demoMapper.batchInsertDemo(groupList);
					session.commit();
					groupList = null;
				}
				session.close();
			} catch (Exception ex) {
	            Console.error(ex.getMessage(), ex);
	        }
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		}
		
		/**
		 * MyBatis foreach 批量插入
		 */
		@Test
		public void batchInsert() {
			StopWatch stopWatch = new StopWatch("MyBatis批量导入【" + dataSize + "】条数据");
			stopWatch.start();
			if(dataSize < batchSize) {
				demoMapper.batchInsertDemo(list);
				list = null;
				stopWatch.stop();
				Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
				return;
			} 
			int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
			for (int i = 0; i < count; i++) {
				int fromIndex = i * batchSize; //计算每批的起始索引
			    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
			    List<Demo> groupList = list.subList(fromIndex, toIndex); //获取子列表
				demoMapper.batchInsertDemo(groupList);
				groupList = null;
			}
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		}
		
		/**
		 * 多线程（Future）批量插入
		 */
		@Test
		public void batchInsert1() {
			StopWatch stopWatch = new StopWatch("多线程1批量导入【" + dataSize + "】条数据");
			stopWatch.start();
			
			if(dataSize < batchSize) {
				demoMapper.batchInsertDemo(list);
				list = null;
				stopWatch.stop();
				Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
				return;
			} 
			
			int nThreads = Runtime.getRuntime().availableProcessors();
			
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			List<Future<Integer>> futures = new ArrayList<Future<Integer>>(nThreads);
			
			int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
			for (int i = 0; i < count; i++) {
				int fromIndex = i * batchSize; //计算每批的起始索引
			    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
			    final List<Demo> groupList = list.subList(fromIndex, toIndex); //获取子列表
				Callable<Integer> task = () -> {
					demoMapper.batchInsertDemo(groupList);
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
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		}
		
		/**
		 * 多线程（CountDownLatch）批量插入
		 */
		@Test
		public void batchInsert2() {
			StopWatch stopWatch = new StopWatch("多线程2批量导入【" + dataSize + "】条数据");
			stopWatch.start();
			if(dataSize < batchSize) {
				demoMapper.batchInsertDemo(list);
				list = null;
				stopWatch.stop();
				Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
				return;
			} 
			
			int nThreads = Runtime.getRuntime().availableProcessors();
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
			CountDownLatch latch = new CountDownLatch(count);
			for (int i = 0; i < count; i++) {
				int fromIndex = i * batchSize; //计算每批的起始索引
			    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
			    final List<Demo> groupList = list.subList(fromIndex, toIndex); //获取子列表
				executorService.submit(() -> {
					try {
						demoMapper.batchInsertDemo(groupList);
					} finally {
						latch.countDown();
					}
				});
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		}
		
		/**
		 * 多线程（CompletableFuture）批量插入
		 */
		@Test
		public void batchInsert3() {
			StopWatch stopWatch = new StopWatch("多线程3批量导入【" + dataSize + "】条数据");
			stopWatch.start();
			if(dataSize < batchSize) {
				demoMapper.batchInsertDemo(list);
				list = null;
				stopWatch.stop();
				Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
				return;
			} 
			
			List<CompletableFuture<Integer>> futures = Lists.newArrayList();
			int nThreads = Runtime.getRuntime().availableProcessors();
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
			for (int i = 0; i < count; i++) {
				int fromIndex = i * batchSize; //计算每批的起始索引
			    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
			    List<Demo> groupList = list.subList(fromIndex, toIndex); //获取子列表
				futures.add(CompletableFuture.supplyAsync(() -> groupList).thenApplyAsync(s -> demoMapper.batchInsertDemo(s), executorService));
			}
			CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
			// 调用join方法等待完成
			allOf.join();
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
		}
}
