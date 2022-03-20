package org.dante.springboot.service;

import org.dante.springboot.bo.springboot.PersonBO;
import org.dante.springboot.mapper.springboot.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PersonService {

	@Autowired
	private PersonMapper personMapper;
	
	@Transactional
	public void insert() {
		PersonBO p1 = new PersonBO();
		p1.setName("p1");
		p1.setAge(20);
		p1.setAddress("p1 地址");
		p1.setUpdateBy("多数据源");
		personMapper.insertPerson(p1);
		
		PersonBO p2 = new PersonBO();
		p2.setName("p2");
		p2.setAge(20);
		p2.setAddress("p2 地址");
		p2.setUpdateBy("多数据源");
		personMapper.insertPerson(p2);
	}
	
}
