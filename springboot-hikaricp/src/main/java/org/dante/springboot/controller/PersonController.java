package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.HobbyDAO;
import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private HobbyDAO hobbyDAO;

	@GetMapping("/persons")
	public List<PersonPO> findPersons() {
		List<PersonPO> persons = personDAO.findAll(Sort.by(Direction.DESC, "id"));
		return persons;
	}
	
	@GetMapping("/hobbys")
	public List<HobbyPO> findHobbys() {
		return hobbyDAO.findAll(Sort.by(Direction.DESC, "person"));
	}
	
}
