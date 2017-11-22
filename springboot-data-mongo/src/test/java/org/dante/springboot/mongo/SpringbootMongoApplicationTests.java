package org.dante.springboot.mongo;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.dante.springboot.mongo.dao.UserDAO;
import org.dante.springboot.mongo.po.UserPO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootMongoApplicationTests {

	@Autowired
	private UserDAO userDAO;

	@Before
	public void setUp() {
//		userDAO.deleteAll();
	}

	@Test
	public void insert() {
		// 创建三个User，并验证User总数
		userDAO.save(new UserPO(1L, "didi", 30, "F"));
		userDAO.save(new UserPO(2L, "mama", 40, "M"));
		userDAO.save(new UserPO(3L, "kaka", 50, "M"));
		Assert.assertEquals(3, userDAO.findAll().size());
		// 删除一个User，再验证User总数
		UserPO u = userDAO.findOne(1L);
		userDAO.delete(u);
		Assert.assertEquals(2, userDAO.findAll().size());
		// 删除一个User，再验证User总数
		u = userDAO.findByUsername("mama");
		userDAO.delete(u);
		Assert.assertEquals(1, userDAO.findAll().size());
	}
	
	@Test
	public void update() {
		UserPO u = userDAO.findOne(3L);
		u.setUsername("dante");
		u.setAge(32);
		userDAO.save(u);
	}

	@Test
	public void saveOne() {
		int count = 5000;
		long start = Date.from(Instant.now()).getTime();
		for (int i = 0; i < count; i++) {
			userDAO.save(new UserPO(new Long(i), "测试" + i, (int) (Math.random() * 20 + Math.random() * 10), (i % 3 == 0 ? "F" : "M")));
		}
		long end = Date.from(Instant.now()).getTime();
		log.info("5000条数据插入，花费时间 {} 毫秒。", end - start);
	}
	
	@Test
	public void saveBatch() {
		List<UserPO> users = new ArrayList<>();
		int count = 5000;
		long start = Date.from(Instant.now()).getTime();
		for (int i = 0; i < count; i++) {
			users.add(new UserPO(new Long(i), "测试" + i, (int) (Math.random() * 20 + Math.random() * 10), (i % 3 == 0 ? "F" : "M")));
		}
		userDAO.insert(users);
		long end = Date.from(Instant.now()).getTime();
		log.info("5000条数据插入，花费时间 {} 毫秒。", end - start);
	}

	@Test
	public void queryByAge() {
		List<UserPO> users = userDAO.queryByAge(26);
		log.info("26岁用户数量 {} , {}" , users.size(), users);
	}
	
	@Test
	public void queryReturnName() {
		List<UserPO> users = userDAO.queryReturnName("测试17");
		log.info("用户 —> {}", users);
	}
	
	@Test
	public void findByUsername() {
		UserPO user = userDAO.findByUsername("测试56");
		log.info("user -> {}.", user);
	}
	
}