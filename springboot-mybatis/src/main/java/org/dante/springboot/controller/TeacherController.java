package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.TeacherMapper;
import org.dante.springboot.po.TeacherPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teacher")
@RestController
public class TeacherController {
	
	@Autowired
	private TeacherMapper teacherMapper;

	@GetMapping("/query/{name}/{post}")
	public TeacherPO queryByNameAndPost(@PathVariable String name, @PathVariable String post) {
		return teacherMapper.queryByNameAndPost(name, post);
	}
	
	@GetMapping("/query_all")
	public List<TeacherPO> queryTeachers() {
		return teacherMapper.queryTeachers();
	}
	
}
