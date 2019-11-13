package org.dante.springboot.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dante.springboot.data.util.RedisClusterUtils;
import org.dante.springboot.data.vo.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringbootDataRedisApplicationTests {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private RedisClusterUtils jedisClusterUtils;

	@Test
	public void saveString() {
		jedisClusterUtils.saveString("x", "bad man");
	}
	
	@Test
	public void saveMultiString() {
		Map<String, String> map = new HashMap<>();
		map.put("aa", "11");
		map.put("bb", "22");
		jedisClusterUtils.saveMultiString(map);
	}
	
	@Test
	public void getStringVal() {
		System.out.println(jedisClusterUtils.getString("x"));
	}
	
	@Test
	public void saveStringExpire() {
		jedisClusterUtils.saveString("x", "bad man 111", 10);
	}
	
	@Test
	public void saveToSet() {
		jedisClusterUtils.saveToSet("x-s", "bad set man");
	}
	
	@Test
	public void getFromSet() {
		String x = jedisClusterUtils.getFromSet("x-s");
		System.out.println(x);
	}
	
	@Test
	public void saveNX() {
		System.out.println(jedisClusterUtils.saveNX("x1", "1111"));
	}
	
	@Test
	public void saveNXExpire() {
		System.out.println(jedisClusterUtils.saveNX("x2", "1111", 5000));
	}
	
	@Test
	public void saveBean() {
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
	public void getBean() {
		try {
			Person p = jedisClusterUtils.getBean("12P", Person.class);
			System.out.println(p.toString());
			List<Person> persons = jedisClusterUtils.getBean("persons_map", List.class);
			System.out.println(persons);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void saveSeq() {
		jedisClusterUtils.saveSeq("seq", 10);
	}
	
	@Test
	public void saveFloat() {
		jedisClusterUtils.saveFloat("seq", 23.4f);
	}
	
	@Test
	public void saveToQueue() {
		jedisClusterUtils.saveToQueue("x-list", "12");
	}
	
	@Test
	public void hashSet() {
		jedisClusterUtils.hashSet("hashSetX", "aa", "bb");
		
		Map<String, String> map = new LinkedHashMap<>();
		map.put("11", "11-a");
		map.put("12", "12-a");
		map.put("13", "13-a");
		jedisClusterUtils.hashSet("hs", map);
		Map<String, Object> m = jedisClusterUtils.hgetAll("hs");
		System.out.println(m);
	}
	
	@Test
	public void hgetAll() throws JsonParseException, JsonMappingException, IOException {
		Person p = new Person("12", "Michale Dante", 32);
		try {
			jedisClusterUtils.hashSet("phash", "1p", p);
			jedisClusterUtils.hashSet("phash", "2p", p);
			jedisClusterUtils.hashSet("phash", "3p", p);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> x = jedisClusterUtils.hgetAll("phash");
		for (Map.Entry<String, Object> entry : x.entrySet()) {  
			  
			Person per = mapper.readValue(entry.getValue().toString(), Person.class) ;
			System.out.println(per.getId() + " -- " + per.getName());
		} 
		System.out.println(x);
		
		
	}
	
	@Test
	public void hashGet() {
		try {
			Person p = jedisClusterUtils.hashGet("phash", "1p", Person.class);
			System.out.println(p);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isExists() {
		System.out.println(jedisClusterUtils.isExists("x"));
		System.out.println(jedisClusterUtils.hashExists("phash", "1p"));
	}
	
	@Test
	public void isMember() {
		System.out.println(jedisClusterUtils.isMember("x-s", "bad set man"));
	}
	
	@Test
	public void delKey() {
		jedisClusterUtils.delKey("ss-s");
		jedisClusterUtils.delKey("aaa");
	}
	
	@Test
	public void listSet() {
		jedisClusterUtils.listSet("x-s").stream().forEach(System.out::println);
	}
	
	@Test
	public void appendSet() {
		jedisClusterUtils.appendSet("x-s", "3729179");
	}
	
	@Test
	public void getMemberScore() {
		jedisClusterUtils.saveToSortedset("zsets", "shuxue", 130D);
		jedisClusterUtils.saveToSortedset("zsets", "a", 123.38);
		jedisClusterUtils.saveToSortedset("zsets", "b", 98.67);
		jedisClusterUtils.saveToSortedset("zsets", "c", 120D);
		System.out.println(jedisClusterUtils.getMemberScore("zsets", "shuxue"));
	}
	
	@Test
	public void listSortedsetRev() {
		Set<TypedTuple<String>> xx = jedisClusterUtils.listSortedsetRev("zsets", 0, -1);
		for (TypedTuple<String> typedTuple : xx) {
			System.out.println(typedTuple.getValue() + " -- " + typedTuple.getScore());
		}
	}
	
}
