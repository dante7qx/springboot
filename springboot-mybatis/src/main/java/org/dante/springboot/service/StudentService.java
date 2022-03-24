package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dao.StudentMapper;
import org.dante.springboot.po.StudentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	private final StudentMapper studentMapper;
	
	@Autowired
	public StudentService(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}
	
	
	public List<StudentPO> queryStudentInAddress(List<String> citys) {
		return studentMapper.queryStudentInAddress(citys);
	}
	
	
}
