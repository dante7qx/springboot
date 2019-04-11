package org.dante.springboot.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Select;
import org.dante.springboot.po.StudentPO;

public interface StudentMapper {
	
	public List<StudentPO> queryStudents();
	
	public StudentPO queryStudentById(Long id);
	
	public StudentPO queryStudentWithAddressById(Long id);
	
	public List<StudentPO> queryStudentByCourseId(Long courseId);
	
	public void insertStudent(StudentPO studentPO);
	
	public void updateStudent(StudentPO studentPO);
	
	public void deleteStudent(Long id);
	
	/**
	 * Mybatis3.5 特性
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from t_student where id = #{id}")
	public Optional<StudentPO> selectStudentById(Long id);
}
