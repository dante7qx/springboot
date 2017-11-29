package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.bo.springboot.PersonBO;
import org.dante.springboot.dao.springboot.PersonDAO;
import org.dante.springboot.mapper.springboot.PersonMapper;
import org.dante.springboot.po.springboot.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private PersonMapper personMapper;

	@GetMapping("/persons/jpa")
	public List<PersonPO> findByJpa() {
		return personDAO.findAll();
	}
	
	@GetMapping("/persons/mybatis")
	public List<PersonBO> findByMybatis() {
		return personMapper.queryPersons();
	}
	
}
