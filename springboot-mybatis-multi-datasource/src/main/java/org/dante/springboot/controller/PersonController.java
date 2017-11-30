package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.bo.springboot.PersonBO;
import org.dante.springboot.mapper.springboot.PersonMapper;
import org.dante.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private PersonService personService;

	@GetMapping("/persons")
	public List<PersonBO> findByMybatis() {
		return personMapper.queryPersons();
	}
	
	@GetMapping("/persons/save")
	public List<PersonBO> saveByMabatis() {
		personService.insert();
		return personMapper.queryPersons();
	}
	
}
