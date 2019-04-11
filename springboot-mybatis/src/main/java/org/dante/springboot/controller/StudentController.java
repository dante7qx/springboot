package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.dao.StudentMapper;
import org.dante.springboot.po.StudentPO;
import org.dante.springboot.service.TeacherStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherStudentService teacherStudentService;

	@PostMapping("/query_page")
	public PageInfo<StudentPO> queryPage() {
		PageHelper.orderBy("age desc");
		PageHelper.startPage(1, 3);
		List<StudentPO> students = studentMapper.queryStudents();
		PageInfo<StudentPO> pageInfo = new PageInfo<>(students);
		return pageInfo;
	}

	@GetMapping("/query_all")
	public List<StudentPO> queryStudents() {
		try {
			teacherStudentService.presistTeacherStudent();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return studentMapper.queryStudents();
	}
	
	@GetMapping("/query/{id}")
	public StudentPO queryById(@PathVariable Long id) {
		return studentMapper.queryStudentById(id);
	}
	
	@GetMapping("/select/{id}")
	public StudentPO selectById(@PathVariable Long id) {
		return studentMapper.selectStudentById(id).orElseThrow(() -> new IllegalArgumentException("This Student does not exit!"));
	}
	
	@GetMapping("/query_with_address/{id}")
	public StudentPO queryWithAddressById(@PathVariable Long id) {
		return studentMapper.queryStudentWithAddressById(id);
	}
	

	@PutMapping("/insert")
	public StudentPO insertStudent(@RequestBody StudentPO studentPO) {
		studentMapper.insertStudent(studentPO);
		return studentPO;
	}

	@PutMapping("/update")
	public StudentPO updateStudent(@RequestBody StudentPO studentPO) {
		studentMapper.updateStudent(studentPO);
		return studentPO;
	}

	@DeleteMapping("/delete/{id}")
	public List<StudentPO> deleteStudent(@PathVariable Long id) {
		studentMapper.deleteStudent(id);
		return studentMapper.queryStudents();
	}
}
