package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.springboot.PersonDAO;
import org.dante.springboot.po.springboot.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;

	@GetMapping("/persons")
	public List<PersonPO> findAll() {
		return personDAO.findAll();
	}
	
}
