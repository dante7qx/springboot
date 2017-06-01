package org.dante.springboot;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.dante.springboot.async.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAsyncApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootAsyncApplicationTests.class);
	
	@Autowired
	private AsyncTask asyncTask;
	
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
