package org.dante.springboot;

import java.util.Arrays;
import java.util.List;

import org.dante.springboot.dao.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootMybatisApplicationTests {
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Test
	public void queryStudentInAddress() {
		List<String> citys = Arrays.asList("北京市", "深圳市");
		studentMapper.queryStudentInAddress(citys).forEach(s -> log.info("Student {}", s));
	}

}
