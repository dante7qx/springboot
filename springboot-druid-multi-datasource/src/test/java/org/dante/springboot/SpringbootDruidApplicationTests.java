package org.dante.springboot;

import java.util.List;

import org.dante.springboot.dao.springboot.PersonDAO;
import org.dante.springboot.po.springboot.PersonPO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootDruidApplicationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootDruidApplicationTests.class);
	
	@Autowired
	private PersonDAO personDAO;
	
	@Test
	public void findAll() {
		List<PersonPO> persons = personDAO.findAll();
		LOGGER.info(persons.toString());
	}

}
