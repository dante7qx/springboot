package org.dante.springboot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.dante.springboot.async.AsyncTask;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootAsyncApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootAsyncApplicationTests.class);
	
	@Autowired
	private AsyncTask asyncTask;
	
	Map<String, Future<String>> map = new HashMap<String, Future<String>>();
	
	@Test
	public void executeTask(){
		try {
			Future<String> task1 = asyncTask.doTask1();
			map.put("a", task1);
			Future<String> task2 = map.get("a");
			for(;;) {  
				LOGGER.info("任务执行状态: {}", task2.isDone());
	            if(task2.isDone()) {  
	            	LOGGER.info("任务执行结果: {}", task2.get());  
	                break;  
	            }  
	            TimeUnit.SECONDS.sleep(1L);
	        } 
	        LOGGER.info("All tasks finished."); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void cancelTask(){
		try {
			Future<String> ct = asyncTask.doCancel();
			if(!ct.isDone()) {
				ct.cancel(true);
			}
	        LOGGER.info("All tasks finished."); 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	
	@Test  
    public void asyncTaskTest() throws InterruptedException, ExecutionException {  
        Future<String> task1 = asyncTask.doTask1();  
        Future<String> task2 = asyncTask.doTask2();  
          
        while(true) {  
            if(task1.isDone() && task2.isDone()) {  
            	LOGGER.info("Task1 result: {}", task1.get());  
            	LOGGER.info("Task2 result: {}", task2.get());  
                break;  
            }  
            Thread.sleep(1000);  
        } 
          
        LOGGER.info("All tasks finished.");  
    } 
	
	@Test  
    public void asyncTaskExecutorTest() throws InterruptedException, ExecutionException {  
        Future<String> task3 = asyncTask.doTask3();  
        Future<String> task4 = asyncTask.doTask4();  
          
        while(true) {  
            if(task3.isDone() && task4.isDone()) {  
            	LOGGER.info("Task3 result: {}", task3.get());  
            	LOGGER.info("Task4 result: {}", task4.get());  
                break;  
            }  
            Thread.sleep(1000);  
        }  
          
        LOGGER.info("All tasks finished.");  
    }

}
