package org.dante.springboot;

import java.util.Arrays;
import java.util.List;

import org.dante.springboot.dao.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
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
