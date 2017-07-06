package org.dante.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.dao.StudentMapper;
import org.dante.springboot.po.StudentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentMapper studentMapper;

	@PostMapping("/query_page")
	public Map<String, Object> queryPage() {
		Map<String, Object> result = new HashMap<>();
		PageHelper.startPage(1, 3);
		List<StudentPO> students = studentMapper.queryStudents();
		PageInfo<StudentPO> pageInfo = new PageInfo<>(students);
		Page<StudentPO> page = (Page<StudentPO>) students;
		result.put("pageInfo", pageInfo);
		result.put("page", page);
		return result;
	}

	@GetMapping("/query_all")
	public List<StudentPO> queryStudents() {
		return studentMapper.queryStudents();
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
