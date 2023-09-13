package org.dante.springboot.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springboot.data.service.QueueService;
import org.dante.springboot.data.util.RedisClusterUtils;
import org.dante.springboot.data.vo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.lang.Console;

@SpringBootTest
@WebAppConfiguration
public class SpringbootDataRedisApplicationTests {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private RedisClusterUtils jedisClusterUtils;
	@Autowired
	private QueueService queueService;

	@Test
	void saveString() {
		jedisClusterUtils.saveString("x", "bad man");
	}
	
	@Test
	void saveMultiString() {
		Map<String, String> map = new HashMap<>();
		map.put("aa", "11");
		map.put("bb", "22");
		jedisClusterUtils.saveMultiString(map);
	}
	
	@Test
	void getStringVal() {
		Console.log(jedisClusterUtils.getString("x"));
	}
	
	@Test
	void saveStringExpire() {
		jedisClusterUtils.saveString("x", "bad man 111", 10);
	}
	
	@Test
	void saveToSet() {
		jedisClusterUtils.saveToSet("x-s", "bad set man");
	}
	
	@Test
	void getFromSet() {
		String x = jedisClusterUtils.getFromSet("x-s");
		Console.log(x);
	}
	
	@Test
	void saveNX() {
		Console.log(jedisClusterUtils.saveNX("x1", "1111"));
	}
	
	@Test
	void saveNXExpire() {
		Console.log(jedisClusterUtils.saveNX("x2", "1111", 5000));
	}
	
	@Test
	void saveBean() {
		List<Person> persons = Arrays.asList(new Person("12", "Michale Dante", 32), new Person("14", "Michale Snake", 45));
		Person p = new Person("12", "Michale Dante", 32);
		try {
			jedisClusterUtils.saveBean("12P", p);
			jedisClusterUtils.saveBean("persons", persons);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@SuppressWarnings("unchecked")
	void getBean() {
		try {
			Person p = jedisClusterUtils.getBean("12P", Person.class);
			Console.log(p.toString());
			List<Person> persons = jedisClusterUtils.getBean("persons_map", List.class);
			Console.log(persons);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void saveSeq() {
		jedisClusterUtils.saveSeq("seq", 10);
	}
	
	@Test
	void saveFloat() {
		jedisClusterUtils.saveFloat("seq", 23.4f);
	}
	
	@Test
	void saveToQueue() {
		jedisClusterUtils.saveToQueue("x-list", "12");
	}
	
	@Test
	void hashSet() {
		jedisClusterUtils.hashSet("hashSetX", "aa", "bb");
		
		Map<String, String> map = new LinkedHashMap<>();
		map.put("11", "11-a");
		map.put("12", "12-a");
		map.put("13", "13-a");
		jedisClusterUtils.hashSet("hs", map);
		Map<String, Object> m = jedisClusterUtils.hgetAll("hs");
		Console.log(m);
	}
	
	@Test
	void hgetAll() throws JsonParseException, JsonMappingException, IOException {
		Person p = new Person("12", "Michale Dante", 32);
		try {
			jedisClusterUtils.hashSet("phash", "1p", p);
			jedisClusterUtils.hashSet("phash", "2p", p);
			jedisClusterUtils.hashSet("phash", "3p", p);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Map<String, Object> x = jedisClusterUtils.hgetAll("phash");
		for (Map.Entry<String, Object> entry : x.entrySet()) {  
			  
			Person per = mapper.readValue(entry.getValue().toString(), Person.class) ;
			Console.log(per.getId() + " -- " + per.getName());
		} 
		Console.log(x);
		
		
	}
	
	@Test
	void hashGet() {
		try {
			Person p = jedisClusterUtils.hashGet("phash", "1p", Person.class);
			Console.log(p);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void isExists() {
		Console.log(jedisClusterUtils.isExists("x"));
		Console.log(jedisClusterUtils.hashExists("phash", "1p"));
	}
	
	@Test
	void isMember() {
		Console.log(jedisClusterUtils.isMember("x-s", "bad set man"));
	}
	
	@Test
	void delKey() {
		jedisClusterUtils.delKey("ss-s");
		jedisClusterUtils.delKey("aaa");
	}
	
	@Test
	void listSet() {
		jedisClusterUtils.listSet("x-s").stream().forEach(System.out::println);
	}
	
	@Test
	void appendSet() {
		jedisClusterUtils.appendSet("x-s", "3729179");
	}
	
	@Test
	void getMemberScore() {
		jedisClusterUtils.saveToSortedset("zsets", "shuxue", 130D);
		jedisClusterUtils.saveToSortedset("zsets", "a", 123.38);
		jedisClusterUtils.saveToSortedset("zsets", "b", 98.67);
		jedisClusterUtils.saveToSortedset("zsets", "c", 120D);
		Console.log(jedisClusterUtils.getMemberScore("zsets", "shuxue"));
	}
	
	@Test
	void listSortedsetRev() {
		Set<TypedTuple<String>> xx = jedisClusterUtils.listSortedsetRev("zsets", 0, -1);
		for (TypedTuple<String> typedTuple : xx) {
			Console.log(typedTuple.getValue() + " -- " + typedTuple.getScore());
		}
	}
	
	@Test
	void queueMQ() throws InterruptedException {
		new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                queueService.sendMessage("Message - " + i);
            }
        }).start();

        new Thread(() -> queueService.onMessage()).start();

        Thread.currentThread().join();
	}
	
}
