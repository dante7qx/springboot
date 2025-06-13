package org.dante.springboot;

import java.util.List;

import org.dante.springboot.dao.HobbyDAO;
import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.lang.Console;

@SpringBootTest
public class SpringbootDruidApplicationTests {
	
	
	@Autowired
	protected PersonDAO personDAO;
	@Autowired
	protected HobbyDAO hobbyDAO;
	
	@Test
	public void findPersons() {
		List<PersonPO> persons = personDAO.findAll();
		Console.log(persons.toString());
	}
	
	@Test
	public void findHobbys() {
		try {
			List<HobbyPO> hobbys = hobbyDAO.findAll();
			Console.log(hobbys.toString());
		} catch (Exception e) {
			Console.error(e.getMessage(), e);
		}
		
	}
	

}
