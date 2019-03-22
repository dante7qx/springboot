package org.dante.springboot;

import java.util.List;

import org.dante.springboot.dao.HobbyDAO;
import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootHikariCPApplicationTests {
	
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
