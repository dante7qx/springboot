package org.dante.springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.dante.springboot.poi.PoiTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootPoiApplicationTests {
	
	Map<String, Future<String>> map = new HashMap<String, Future<String>>();
	
	@Autowired
	private PoiTask poiTest;
	
	@Test
	public void test() {
		try {
			File file = new File("/Users/dante/Desktop/HiApp财务结算系统服务器.xlsx");
			InputStream ins = new FileInputStream(file);
			Future<String> task1 = poiTest.poiTest(ins);
			map.put("a", task1);
			Future<String> task2 = map.get("a");
			while(true) {  
				System.out.println(task2.isDone());
	            if(task2.isDone()) {  
	                break;  
	            }  
	            Thread.sleep(1000);  
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
