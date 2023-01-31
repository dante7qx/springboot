package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.annotation.EncryptMethod;
import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	@Value("${spirit.pwd}")
	private String pwd;

	@EncryptMethod
	@GetMapping("/all")
	public List<PersonPO> findAll() {
		log.info("pwd -> {}", pwd);
		return personDAO.findAll();
	}
	
	@PostMapping("/add")
	public PersonPO addPerson(@RequestBody PersonPO person) {
		person.setUpdateDate(DateUtil.date());
		PersonPO result = personDAO.save(person);
		log.info("person: {} - result: {}", person, result);
		return result; 
	}
	
}
