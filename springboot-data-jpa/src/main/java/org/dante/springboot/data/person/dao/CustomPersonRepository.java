package org.dante.springboot.data.person.dao;

import org.dante.springboot.data.person.domain.Person;
import org.dante.springboot.data.pub.service.CustomRepository;

public interface CustomPersonRepository extends CustomRepository<Person, Integer>{

}
