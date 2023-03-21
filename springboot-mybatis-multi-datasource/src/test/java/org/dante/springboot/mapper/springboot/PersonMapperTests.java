package org.dante.springboot.mapper.springboot;

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

import org.dante.springboot.SpringbootDruidApplicationTests;
import org.dante.springboot.bo.springboot.PersonBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;

/**
 * 
	测试结果
	
	操作系统：MacOS 13.2.1
	CPU: 2.3 GHz 八核Intel Core i9
	内存：32 GB 2667 MHz DDR4
	硬盘：APPLE SSD AP1024N
	
	StopWatch 'MyBatis批量导入【5000】条数据': running time = 1386 ms
	StopWatch '多线程1批量导入【5000】条数据': running time = 1210 ms	单次2000
	StopWatch '多线程2批量导入【5000】条数据': running time = 1247 ms	单次2000
	StopWatch '多线程3批量导入【5000】条数据': running time = 1233 ms	单次2000
	
	StopWatch 'MyBatis批量导入【10000】条数据': running time = 1493 ms
	StopWatch '多线程1批量导入【10000】条数据': running time = 1572 ms
	StopWatch '多线程2批量导入【10000】条数据': running time = 1534 ms
	StopWatch '多线程3批量导入【10000】条数据': running time = 1566 ms
	
	StopWatch 'MyBatis批量导入【100000】条数据': running time = 4347 ms
	StopWatch '多线程1批量导入【100000】条数据': running time = 2717 ms
	StopWatch '多线程2批量导入【100000】条数据': running time = 2888 ms
	StopWatch '多线程3批量导入【100000】条数据': running time = 2950 ms
	
	StopWatch 'MyBatis批量导入【1000000】条数据': running time = 35 s
	StopWatch '多线程1批量导入【1000000】条数据': running time = 12 s
	StopWatch '多线程2批量导入【1000000】条数据': running time = 10 s
	StopWatch '多线程3批量导入【1000000】条数据': running time = 10 s
	
	StopWatch 'MyBatis批量导入【10000000】条数据': running time = 340 s
	StopWatch '多线程1批量导入【10000000】条数据': running time = 183 s
	StopWatch '多线程2批量导入【10000000】条数据': running time = 176 s
	StopWatch '多线程3批量导入【10000000】条数据': running time = 176 s
	
	参考资料：https://segmentfault.com/a/1190000042671387
 * 
 * @author dante
 *
 */
@Slf4j
public class PersonMapperTests extends SpringbootDruidApplicationTests {

	@Autowired
	private PersonMapper personMapper;
	
	// 百万数据，单次批量10000
	private static int dataSize = 1000000;		// 数据集数量
	private static int batchSize = 10000;	    // 单次批处理数量
	
	// 千万数据，单次批量100000
//	private static int dataSize = 10000000;		// 数据集数量
//	private static int batchSize = 100000;	    // 单次批处理数量
	
	
	private static List<PersonBO> list = new LinkedList<>();

	@BeforeEach
	public void init() {
		for (int i = 0; i < dataSize; i++) {
			PersonBO po = new PersonBO();
			po.setName("测试数据【" + i + "】");
			po.setAddress("测试地址【" + i + "】");
			po.setAge(30);
			po.setUpdateBy("系统批量插入");
			list.add(po);
		}
		log.info("*******************************数据初始化完成*******************************");
	}
	
	/**
	 * 循环单条插入，数据量小于500时，可以适用
	 */
	@Test
	public void circulationInsert() {
		if(dataSize > 500) {
			log.info("MyBatis循环导入，数据不能超过500条！");
			return;
		}
		StopWatch stopWatch = new StopWatch("MyBatis循环导入【" + dataSize + "】条数据");
		stopWatch.start();
		for (PersonBO person : list) {
			personMapper.insertPerson(person);
		}
		list = null;
		stopWatch.stop();
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
	
	/**
	 * MyBatis批量插入
	 */
	@Test
	public void batchInsert() {
		StopWatch stopWatch = new StopWatch("MyBatis批量导入【" + dataSize + "】条数据");
		stopWatch.start();
		if(dataSize < batchSize) {
			personMapper.insertPersons(list);
			list = null;
			stopWatch.stop();
			Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
			return;
		} 
		int count = (dataSize + batchSize - 1) / batchSize; //计算需要分多少批
		for (int i = 0; i < count; i++) {
			int fromIndex = i * batchSize; //计算每批的起始索引
		    int toIndex = Math.min(fromIndex + batchSize, dataSize); //计算每批的结束索引，注意不要越界
		    List<PersonBO> groupList = list.subList(fromIndex, toIndex); //获取子列表
			personMapper.insertPersons(groupList);
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
			personMapper.insertPersons(list);
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
		    final List<PersonBO> groupList = list.subList(fromIndex, toIndex); //获取子列表
			Callable<Integer> task = () -> {
				personMapper.insertPersons(groupList);
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
			personMapper.insertPersons(list);
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
		    final List<PersonBO> groupList = list.subList(fromIndex, toIndex); //获取子列表
			executorService.submit(() -> {
				try {
					personMapper.insertPersons(groupList);
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
			personMapper.insertPersons(list);
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
		    List<PersonBO> groupList = list.subList(fromIndex, toIndex); //获取子列表
			futures.add(CompletableFuture.supplyAsync(() -> groupList).thenApplyAsync(s -> personMapper.insertPersons(s), executorService));
		}
		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		// 调用join方法等待完成
		allOf.join();
		list = null;
		stopWatch.stop();
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
}
