package org.dante.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
	
	@Autowired
	private StudentService studentService;

}
