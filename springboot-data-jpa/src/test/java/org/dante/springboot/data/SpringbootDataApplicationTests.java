package org.dante.springboot.data;

import java.util.List;

import org.dante.springboot.data.person.dao.PersonRepository;
import org.dante.springboot.data.person.domain.TestNativeSql;
import org.dante.springboot.data.utils.JpaEntityConvertUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDataApplicationTests {
	
	@Autowired
	private PersonRepository personRepository;
	

	@Test
	public void test() throws Exception {
		List<Object[]> objs = personRepository.findTest();
		List<TestNativeSql> list = JpaEntityConvertUtils.castEntity(objs, TestNativeSql.class);
		System.out.println(list);
	}
	
	@Test
	public void findByIdAndAgeOrNameAndAddress() {
		personRepository.findByIdAndAgeOrNameAndAddress(11, 20, "dante", "大道");
	}
}
