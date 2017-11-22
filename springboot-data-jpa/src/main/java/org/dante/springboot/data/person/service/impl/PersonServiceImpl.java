package org.dante.springboot.data.person.service.impl;

import java.util.List;

import org.dante.springboot.data.person.dao.PersonRepository;
import org.dante.springboot.data.person.domain.Person;
import org.dante.springboot.data.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	@Transactional
	public Person savePerson(Person p) {
		Person person = personRepository.save(p);
		Person p2 = new Person("1111", 22, "1111");
		personRepository.save(p2);
		p2 = null;
		personRepository.save(p2);
		return person;
	}

	@Override
	@Transactional(rollbackFor= {IllegalArgumentException.class})
	public Person savePersonRollback(Person p) {
		Person person = personRepository.save(p);
		if("dante".equalsIgnoreCase(p.getName())) {
			throw new IllegalArgumentException("dante已存在，数据回滚");
		}
		return person;
	}

	@Override
	@Transactional(noRollbackFor={IllegalArgumentException.class})
	public Person savePersonNoRollback(Person p) {
		Person person = personRepository.save(p);
		if("dante".equalsIgnoreCase(p.getName())) {
			throw new IllegalArgumentException("dante虽已存在，但数据不会回滚");
		}
		return person;
	}

	@Override
	public List<Person> findByName(String name) throws Exception {
		return personRepository.findByName(name);
	}

}
