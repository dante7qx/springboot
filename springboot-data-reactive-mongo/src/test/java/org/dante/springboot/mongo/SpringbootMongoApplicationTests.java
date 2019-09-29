package org.dante.springboot.mongo;

import org.dante.springboot.mongo.dao.UserDAO;
import org.dante.springboot.mongo.po.UserPO;
import org.dante.springboot.mongo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootMongoApplicationTests {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MongoTemplate template;
	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
		userDAO.deleteAll();
	}

	@Test
	public void insert() {
		// 创建三个User，并验证User总数
		userDAO.save(new UserPO(1L, "zhazha", 30, "F", "2017-11-11")).block();
		userDAO.save(new UserPO(2L, "mama", 40, "M", "2017-11-11")).block();
		userDAO.save(new UserPO(3L, "kaka", 50, "M", "2017-11-11")).block();
		/*
		 * Assert.assertEquals(3, userDAO.findAll().size()); // 删除一个User，再验证User总数
		 * UserPO u = userDAO.findById(1L).get(); userDAO.delete(u);
		 * Assert.assertEquals(2, userDAO.findAll().size()); // 删除一个User，再验证User总数 u =
		 * userDAO.findByUsername("mama"); userDAO.delete(u); Assert.assertEquals(1,
		 * userDAO.findAll().size());
		 */
	}

	@Test
	public void update() {
		Mono<UserPO> user = userDAO.findById(1L);
		user.subscribe(u -> {
			u.setUsername("dante");
			u.setAge(32);
			userDAO.save(u);
		});
	}
}
