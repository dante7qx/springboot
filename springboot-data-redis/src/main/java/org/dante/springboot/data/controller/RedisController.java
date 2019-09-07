package org.dante.springboot.data.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.dante.springboot.data.util.JedisClusterUtils;
import org.dante.springboot.data.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

	@Autowired
	private JedisClusterUtils jedisClusterUtils;
	
	@GetMapping("get/{key}")
	public String getKey(@PathVariable String key) {
		return jedisClusterUtils.getString(key);
	}

	@GetMapping("/key/{str}")
	public String strStore(@PathVariable String str) {
		jedisClusterUtils.saveString("str", str.concat("-").concat(Instant.now().toString()));
		return jedisClusterUtils.getString("str");
	}

	@GetMapping("/bean/{personName}")
	public Person beanStore(@PathVariable String personName) {
		List<Person> persons = Arrays.asList(new Person("12", "Michale Dante", 32),
				new Person("14", "Michale Snake", 45));
		Person p = new Person("12", personName, 32);
		Person storePerson = null;
		try {
			jedisClusterUtils.saveBean("12P", p);
			jedisClusterUtils.saveBean("persons", persons);
			storePerson = jedisClusterUtils.getBean("12P", Person.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storePerson;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/beans/{personName}")
	public List<Person> beansStore(@PathVariable String personName) {
		List<Person> persons = Arrays.asList(new Person("12", personName + "-1", 32),
				new Person("14", personName + "-2", 45));
		List<Person> storePersons = null;
		try {
			jedisClusterUtils.saveBean("persons", persons);
			storePersons = jedisClusterUtils.getBean("persons", List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storePersons;
	}

}
