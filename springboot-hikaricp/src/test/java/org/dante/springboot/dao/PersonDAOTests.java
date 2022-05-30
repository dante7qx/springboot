package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.SpringbootHikariCPApplicationTests;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonDAOTests extends SpringbootHikariCPApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootHikariCPApplicationTests.class);

	@Autowired
	protected PersonDAO personDAO;
	@Autowired
	protected HobbyDAO hobbyDAO;

	@Test
	public void findPersons() {
		List<PersonPO> persons = personDAO.findAll();
		LOGGER.info(persons.toString());
	}

	@Test
	public void findHobbys() {
		List<HobbyPO> hobbys = hobbyDAO.findAll();
		LOGGER.info(hobbys.toString());
	}
}
