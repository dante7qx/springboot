package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.po.TeacherPO;

public interface TeacherMapper {
	
	public List<TeacherPO> queryTeachers();
	
	public TeacherPO queryTeacherById(Long id);
	
	public void insertTeacher(TeacherPO teacherPO);
	
	public void updateTeacher(TeacherPO teacherPO);
	
	public void deleteTeacher(Long id);
}
