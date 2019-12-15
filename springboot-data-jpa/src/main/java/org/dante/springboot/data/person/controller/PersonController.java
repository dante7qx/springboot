package org.dante.springboot.data.person.controller;

import java.util.List;

import org.dante.springboot.data.person.dao.CustomPersonRepository;
import org.dante.springboot.data.person.dao.PersonRepository;
import org.dante.springboot.data.person.dao.PersonSpecs;
import org.dante.springboot.data.person.domain.Person;
import org.dante.springboot.data.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private CustomPersonRepository customPersonRepository;
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/insert")
	public Person insert(String name, int age, String address, String remark) {
		Person p = personRepository.save(new Person(name, age, address, remark));
		return p;
	}
	
	@RequestMapping("/q1")
	public List<Person> queryByName(String name) {
		List<Person> p = null;
		try {
			p = personService.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	@RequestMapping("/q2")
	public List<Person> queryByAddressLike(String address) {
		List<Person> list = null;
		try {
			list = personRepository.findByAddressLike("%"+address+"%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/q3")
	public Person queryByNameAndAddress(String name, String address) {
		Person p = null;
		try {
			p = personRepository.withNameAndAddressQuery(name, address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	@RequestMapping("/q4")
	public Person queryByNameAndAddressWithNameQuery(String name, String address) {
		Person p = null;
		try {
			p = personRepository.withNameAndAddressNamedQuery(name, address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	@RequestMapping("/bj")
	public List<Person> queryBeijingPerson() {
		List<Person> persons = null;
		try {
			persons = personRepository.findAll(PersonSpecs.personFromBeiJing());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persons;
	}
	
	@RequestMapping("/sort")
	public List<Person> sort() {
		List<Person> list = personRepository.findAll(Sort.by(Sort.Direction.ASC, "age"));
		return list;
	}
	
	@RequestMapping("/page")
	public Page<Person> page(int page, int pageSize) {
		Page<Person> pages = personRepository.findAll(PageRequest.of(page, pageSize));
		return pages;
	}
	
	@RequestMapping("/auto")
	public Page<Person> autoSearch(Person person) {
		Page<Person> pages = customPersonRepository.findByAuto(person, PageRequest.of(0, 4));
		return pages;
	}
	
	@RequestMapping("/queryhobby")
	public List<Person> queryByHobby(String hobby) {
		List<Person> list = null;
		try {
			list = personRepository.findAll(PersonSpecs.queryPersonByHobby(hobby));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/transact")
	public Person transact(String name, int age, String address) {
		Person p = personService.savePerson(new Person(name, age, address));
		return p;
	}
	
	@RequestMapping("/rollback")
	public Person rollback(String name, int age, String address) {
		Person p = personService.savePersonRollback(new Person(name, age, address));
		return p;
	}
	
	@RequestMapping("/norollback")
	public Person norollback(String name, int age, String address) {
		Person p = personService.savePersonNoRollback(new Person(name, age, address));
		return p;
	}
}
