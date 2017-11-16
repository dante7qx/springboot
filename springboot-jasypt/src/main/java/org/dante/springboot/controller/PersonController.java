package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	@Value("${spirit.pwd}")
	private String pwd;

	@GetMapping("/all")
	public List<PersonPO> findAll() {
		log.info("pwd -> {}", pwd);
		return personDAO.findAll();
	}
	
}
