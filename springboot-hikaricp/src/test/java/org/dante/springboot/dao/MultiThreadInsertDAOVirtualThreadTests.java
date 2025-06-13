package org.dante.springboot.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.SpringbootHikariCPApplicationTests;
import org.dante.springboot.po.MultiThreadInsertPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用虚拟线程来实现
 * 
 */
@Slf4j
public class MultiThreadInsertDAOVirtualThreadTests extends SpringbootHikariCPApplicationTests {

	@Autowired
	private MultiThreadInsertDAO multiThreadInsertDAO;

	private static int dataSize = 100000;
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
		StopWatch stopWatch = new StopWatch("虚拟线程批量导入【" + dataSize + "】条数据");
		stopWatch.start();
		
		int nThreads = Runtime.getRuntime().availableProcessors();
		int subSize = dataSize / nThreads;
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Future<Integer>> futures = new ArrayList<Future<Integer>>(nThreads);
			for (int i = 0; i < nThreads; i++) {
				int fromIndex = i * subSize;
				int toIndex = (i + 1) * subSize;
				if (i == nThreads - 1) {
					toIndex = dataSize; // 最后一个子列表包含剩余的元素
				}
				final List<MultiThreadInsertPO> groupList = list.subList(fromIndex, toIndex);
				futures.add(executor.submit(() -> {
	                multiThreadInsertDAO.saveAll(groupList);
	                return 1;
	            }));
			}
			// 等待所有任务完成（虚拟线程无需复杂等待逻辑）
	        for (Future<Integer> future : futures) {
	            try {
	                future.get(); // 阻塞直到任务完成
	            } catch (InterruptedException | ExecutionException e) {
	                Thread.currentThread().interrupt();
	                throw new RuntimeException("任务执行失败", e);
	            }
	        }
		}	// 自动关闭executor（实现了AutoCloseable）
		
		/**
		 	// 结构化并发
		 	try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
		    List<Future<Integer>> futures = list.stream()
		        .map(chunk -> scope.fork(() -> {
		            multiThreadInsertDAO.saveAll(chunk);
		            return 1;
		        }))
		        .toList();
		
		    scope.join();          // 等待所有任务完成
		    scope.throwIfFailed(); // 如果有任务失败则抛出异常
		}
		 */
		
		stopWatch.stop();
		// 打印出耗时
		Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
	}
	
	/**
	 * 结构化并发
	 * @throws ExecutionException 
	 */
	@Test
	public void batchInsert2() throws InterruptedException, ExecutionException {
		StopWatch stopWatch = new StopWatch("结构化并发批量导入【" + dataSize + "】条数据");
		stopWatch.start();
		
		int nThreads = Runtime.getRuntime().availableProcessors();
		int subSize = dataSize / nThreads;
		try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
			List<StructuredTaskScope.Subtask<Integer>> subtasks = new ArrayList<>(nThreads);
			// 分割任务并提交
	        for (int i = 0; i < nThreads; i++) {
	            int fromIndex = i * subSize;
	            int toIndex = (i + 1) * subSize;
	            if (i == nThreads - 1) {
	                toIndex = dataSize;
	            }
	            final List<MultiThreadInsertPO> groupList = list.subList(fromIndex, toIndex);

	            // 使用 fork() 提交任务（返回 Subtask）
	            subtasks.add(scope.fork(() -> {
	                multiThreadInsertDAO.saveAll(groupList);
	                return 1;
	            }));
	        }
	        // 等待所有任务完成或失败
	        scope.join();          // 阻塞直到所有子任务完成
	        scope.throwIfFailed(); // 如果有任务失败，抛出异常
	        
	        // 获取结果（如果需要）
	        /*
	        for (var subtask : subtasks) {
	            Console.log("子任务状态: {}", subtask.state());
	        }
	        */
		}	// 自动关闭scope
		
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
