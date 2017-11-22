package org.dante.springboot.data.person.service;

import java.util.List;

import org.dante.springboot.data.person.domain.Person;

public interface PersonService {
	public List<Person> findByName(String name) throws Exception;
	
	public Person savePerson(Person p);
	
	public Person savePersonRollback(Person p);
	
	public Person savePersonNoRollback(Person p);
}
