package org.dante.springboot.dao;

import java.util.List;
import java.util.Map;

import org.dante.springboot.po.CoursePO;

public interface CourseMapper {
	
	public CoursePO queryCourseById(Long id);
	
	public CoursePO queryCourseById2(Long id);
	
	public List<CoursePO> queryCourses(Map<String, Object> params);
	
	public void updateCourse(CoursePO coursePO);
}
