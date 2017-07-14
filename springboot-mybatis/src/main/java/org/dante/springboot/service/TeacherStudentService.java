package org.dante.springboot.service;

import java.time.Instant;
import java.util.Date;

import org.dante.springboot.dao.StudentMapper;
import org.dante.springboot.dao.TeacherMapper;
import org.dante.springboot.po.StudentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
public class TeacherStudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	
	@Transactional
	public void presistTeacherStudent() {
		StudentPO studentPO = new StudentPO();
		studentPO.setId(1000L);
		studentPO.setName("Test - 1000");
		studentPO.setAge(1000);
		studentPO.setUpdateDate(Date.from(Instant.now()));
		studentMapper.insertStudent(studentPO);
		
		teacherMapper.insertTeacher(null);
	}

}
