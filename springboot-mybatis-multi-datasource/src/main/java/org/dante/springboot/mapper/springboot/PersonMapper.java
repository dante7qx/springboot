package org.dante.springboot.mapper.springboot;

import java.util.List;

import org.dante.springboot.bo.springboot.PersonBO;

public interface PersonMapper {
	
	public List<PersonBO> queryPersons();
	
	public int insertPerson(PersonBO person);
	
	public int insertPersons(List<PersonBO> persons);
	
}
