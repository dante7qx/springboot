package org.dante.springboot.mongo;

import java.sql.Date;
import java.time.Instant;

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
		userDAO.deleteAll();
	}

	@Test
	public void insert() {
		// 创建三个User，并验证User总数
		userDAO.save(new UserPO(1L, "didi", 30));
		userDAO.save(new UserPO(2L, "mama", 40));
		userDAO.save(new UserPO(3L, "kaka", 50));
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
	public void saveBatch() {
		int count = 5000;
		long start = Date.from(Instant.now()).getTime();
		for (int i = 0; i < count; i++) {
			userDAO.save(new UserPO(new Long(i), "测试" + i, (int) (Math.random() * 20 + Math.random() * 10)));
		}
		long end = Date.from(Instant.now()).getTime();
		log.info("5000条数据插入，花费时间 {} 毫秒。", end - start);
	}

}
