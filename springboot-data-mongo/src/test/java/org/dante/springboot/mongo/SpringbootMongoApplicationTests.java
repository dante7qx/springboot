package org.dante.springboot.mongo;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.dante.springboot.mongo.dao.UserDAO;
import org.dante.springboot.mongo.page.PageReq;
import org.dante.springboot.mongo.page.SpiritMongoPageable;
import org.dante.springboot.mongo.po.UserPO;
import org.dante.springboot.mongo.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

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
		userDAO.save(new UserPO(1L, "didi", 30, "F", "2017-11-11"));
		userDAO.save(new UserPO(2L, "mama", 40, "M", "2017-11-11"));
		userDAO.save(new UserPO(3L, "kaka", 50, "M", "2017-11-11"));
		Assert.assertEquals(3, userDAO.findAll().size());
		// 删除一个User，再验证User总数
		UserPO u = userDAO.findById(1L).get();
		userDAO.delete(u);
		Assert.assertEquals(2, userDAO.findAll().size());
		// 删除一个User，再验证User总数
		u = userDAO.findByUsername("mama");
		userDAO.delete(u);
		Assert.assertEquals(1, userDAO.findAll().size());
	}

	@Test
	public void update() {
		UserPO u = userDAO.findById(1L).get();
		u.setUsername("dante");
		u.setAge(32);
		userDAO.save(u);
	}

	@Test
	public void saveOne() {
		int count = 50;
		long start = Date.from(Instant.now()).getTime();
		for (int i = 0; i < count; i++) {
			UserPO user = new UserPO(new Long(i), "测试" + i, (int) (Math.random() * 20 + Math.random() * 10),
					(i % 3 == 0 ? "F" : "M"), "2017-11-11");
			user.setUpdateDate(Date.from(Instant.now()));
			userDAO.save(user);
		}
		long end = Date.from(Instant.now()).getTime();
		log.info("5000条数据插入，花费时间 {} 毫秒。", end - start);
	}

	@Test
	public void saveBatch() {
		List<UserPO> users = new ArrayList<>();
		int count = 50;
		long start = Date.from(Instant.now()).getTime();
		for (int i = 0; i < count; i++) {
			users.add(new UserPO(new Long(i), "测试" + i, (int) (Math.random() * 20 + Math.random() * 10),
					(i % 3 == 0 ? "F" : "M"), "2017-11-11"));
		}
		userDAO.insert(users);
		long end = Date.from(Instant.now()).getTime();
		log.info("5000条数据插入，花费时间 {} 毫秒。", end - start);
	}

	@Test
	public void queryByAge() {
		List<UserPO> users = userDAO.queryByAge(23);
		log.info("23岁用户数量 {} , {}", users.size(), users);
	}

	@Test
	public void queryByAgePage() {
		Sort sortAge = new Sort(Direction.DESC, "id");
		Pageable pageable = PageRequest.of(1, 5, sortAge);
		Page<UserPO> resp = userDAO.findByAge(23, pageable);
		resp.getContent().forEach(x -> log.info(x.toString()));
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

	@Test
	public void findPage() {
		int page = 1;
		int pageSize = 20;
		Sort sortAge = new Sort(Direction.ASC, "age");
		Pageable pageable = PageRequest.of(page, pageSize, sortAge);
		Page<UserPO> pageResult = userDAO.findAll(pageable);
		log.info(pageResult.toString());
	}

	/**
	 * 使用 Query、Criteria、MongoTemplate 进行查询分页
	 */
	@Test
	public void queryPageWithMongoTemplate() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("name").regex(".*?" + "30" + ".*");
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "age"));
		Sort sort = Sort.by(orders);
		if (null != sort) {
			query.with(sort);
		}
		query.addCriteria(criteria);
		SpiritMongoPageable pageable = new SpiritMongoPageable();
		// 开始页
		pageable.setPagenumber(0);
		// 每页条数
		pageable.setPagesize(10);
		// 排序
		pageable.setSort(sort);
		// 查询出一共的条数
		Long count = template.count(query, UserPO.class);
		// 查询
		List<UserPO> list = template.find(query.with(pageable), UserPO.class);
		// 将集合与分页结果封装
		Page<UserPO> resp = new PageImpl<UserPO>(list, pageable, count);
		resp.getContent().forEach(x -> log.info(x.toString()));
	}
	
	@Test
	public void testPageTemplate() {
		PageReq pageReq = new PageReq();
		pageReq.setSort("id");
		pageReq.setOrder("desc");
		pageReq.setRows(5);
		pageReq.getQ().put("age", 17);
		Page<UserPO> resp = userService.queryPage(pageReq, UserPO.class);
		resp.getContent().forEach(x -> log.info(x.toString()));
	}
}
