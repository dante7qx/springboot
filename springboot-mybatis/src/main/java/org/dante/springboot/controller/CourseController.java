package org.dante.springboot.controller;

import org.dante.springboot.dao.CourseMapper;
import org.dante.springboot.po.CoursePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseMapper courseMapper;

	@GetMapping("/query/{id}")
	public CoursePO queryById(@PathVariable Long id) {
		return courseMapper.queryCourseById(id);
	}
	
	@GetMapping("/query2/{id}")
	public CoursePO queryById2(@PathVariable Long id) {
		return courseMapper.queryCourseById2(id);
	}
}
