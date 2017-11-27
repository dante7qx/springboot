package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dao.TestDAO;
import org.dante.springboot.po.TestPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TestService {

	@Autowired
	private TestDAO testDAO;
	
	public List<TestPO> findTests() {
		return testDAO.findAll();
	}
	
	@Transactional
	public void persist(TestPO testPO) {
		testDAO.save(testPO); 
	}
	
}
