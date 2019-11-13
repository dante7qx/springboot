package org.dante.springboot.data.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.dante.springboot.data.util.RedisClusterUtils;
import org.dante.springboot.data.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RedisController {

	@Autowired
	private RedisClusterUtils redisClusterUtils;
	
	@GetMapping("get/{key}")
	public String getKey(@PathVariable String key) {
		return redisClusterUtils.getString(key);
	}

	@GetMapping("/key/{str}")
	public String strStore(@PathVariable String str) {
		redisClusterUtils.saveString("str", str.concat("-").concat(Instant.now().toString()));
		return redisClusterUtils.getString("str");
	}

	@GetMapping("/bean/{personName}")
	public Person beanStore(@PathVariable String personName) {
		List<Person> persons = Arrays.asList(new Person("12", "Michale Dante", 32),
				new Person("14", "Michale Snake", 45));
		Person p = new Person("12", personName, 32);
		Person storePerson = null;
		try {
			redisClusterUtils.saveBean("12P", p);
			redisClusterUtils.saveBean("persons", persons);
			storePerson = redisClusterUtils.getBean("12P", Person.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
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
			redisClusterUtils.saveBean("persons", persons);
			storePersons = redisClusterUtils.getBean("persons", List.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return storePersons;
	}

}
