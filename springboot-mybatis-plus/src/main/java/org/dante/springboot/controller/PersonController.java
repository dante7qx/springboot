package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	

	@GetMapping("/persons")
	public List<PersonPO> findPersons() {
		return null;
	}
	
	@GetMapping("/hobbys")
	public List<HobbyPO> findHobbys() {
		return null;
	}
	
}
