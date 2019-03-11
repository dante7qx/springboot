package org.dante.springboot.service;

import org.dante.springboot.po.PersonPO;

public interface IPersonService {
	
	public void savePerson(PersonPO person);
	
	/**
	 * Transaction rolled back because it has been marked as rollback-only
	 * 
	 * @param person
	 */
	public void savePersonRollbackOnly(PersonPO person);
	
}
