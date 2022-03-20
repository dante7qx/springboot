package org.dante.springboot.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.dante.springboot.SpringbootFlowableDesignApplicationTests;
import org.dante.springboot.po.TestPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDAOTests extends SpringbootFlowableDesignApplicationTests {
	
	@Autowired
	private TestDAO testDAO;
	
	@Test
	public void queryAll() {
		List<TestPO> list = testDAO.findAll();
		log.info("List=> {}", list);
		assertTrue(list.size() >= 0);
	}
	
}
