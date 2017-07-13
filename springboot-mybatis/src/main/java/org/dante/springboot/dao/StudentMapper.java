package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.StudentPO;

public interface StudentMapper {
	
	public List<StudentPO> queryStudents();
	
	public StudentPO queryStudentById(Long id);
	
	public void insertStudent(StudentPO studentPO);
	
	public void updateStudent(StudentPO studentPO);
	
	public void deleteStudent(Long id);
}
