package org.dante.springboot.dao;

import org.dante.springboot.po.CoursePO;

public interface CourseMapper {
	
	public CoursePO queryCourseById(Long id);
	
	public CoursePO queryCourseById2(Long id);
	
}
