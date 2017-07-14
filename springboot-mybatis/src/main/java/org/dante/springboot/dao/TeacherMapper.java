package org.dante.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.dante.springboot.po.TeacherPO;

public interface TeacherMapper {
	
	public List<TeacherPO> queryTeachers();
	
	public TeacherPO queryTeacherById(Long id);
	
	/**
	 * 多参数查询
	 * 
	 * @param name
	 * @param post
	 * @return
	 */
	public TeacherPO queryByNameAndPost(@Param("name") String name, @Param("post") String post);
	
	public void insertTeacher(TeacherPO teacherPO);
	
	public void updateTeacher(TeacherPO teacherPO);
	
	public void deleteTeacher(Long id);
}
