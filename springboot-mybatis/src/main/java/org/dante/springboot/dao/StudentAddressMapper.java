package org.dante.springboot.dao;

import org.dante.springboot.po.StudentAddressPO;

public interface StudentAddressMapper {
	
	public StudentAddressPO queryStudentAddressById(Long id);
	
}
