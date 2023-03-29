package org.dante.springboot;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.dante.springboot.po.UserPO;
import org.dante.springboot.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootJDBCApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void insert() {
		UserPO user = new UserPO();
		user.setName("无尽");
		user.setAge(200);
		userService.insert(user);
	}
	
	@Test
	public void insertReturnId() {
		UserPO user = new UserPO();
		user.setName("沐");
		user.setAge(25);
		userService.insertReturnId(user);
		log.info("user ==> {}", user);
	}
	
	@Test
	public void batchInsert() {
		List<UserPO> users = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			UserPO user = new UserPO();
			user.setName("batch" + i);
			user.setAge(i + 20);
			users.add(user);
		}
		userService.batchInsert(users);
	}
	
	@Test
	public void update() {
		UserPO user = new UserPO();
		user.setId(1L);
		user.setAge(33);
		userService.update(user);
	}
	
	@Test
	public void deleteById() {
		userService.deleteById(1L);
	}
	
	@Test
	public void queryUsers() {
		List<UserPO> users = userService.queryUsers();
		users.forEach(u -> log.info("user ==> {}", u));
		assertNotNull(users);
	}
	
	@AfterAll
	public void showUsers() {
		List<UserPO> users = userService.queryUsers();
		users.forEach(u -> log.info("user ==> {}", u));
	}
	
}
