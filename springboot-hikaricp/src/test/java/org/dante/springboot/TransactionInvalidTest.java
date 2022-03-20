package org.dante.springboot;

import org.dante.springboot.po.PersonPO;
import org.dante.springboot.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionInvalidTest extends SpringbootHikariCPApplicationTests {
	
	@Autowired
	private IPersonService personService;
	
	@Test
	public void savePerson() {
		PersonPO person = new PersonPO(18L);
		personService.savePerson(person);
	}
	
	@Test
	public void savePersonRollbackOnly() {
		PersonPO person = new PersonPO(18L);
		personService.savePersonRollbackOnly(person);
	}
	
}
