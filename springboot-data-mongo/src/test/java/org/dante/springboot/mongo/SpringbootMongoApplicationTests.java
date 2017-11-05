package org.dante.springboot.mongo;

import org.dante.springboot.mongo.dao.UserDAO;
import org.dante.springboot.mongo.po.UserPO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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

}
