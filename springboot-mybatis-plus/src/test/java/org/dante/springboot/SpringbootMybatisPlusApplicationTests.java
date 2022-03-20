package org.dante.springboot;

import java.util.List;

import org.dante.springboot.mapper.PersonMapper;
import org.dante.springboot.po.PersonPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootMybatisPlusApplicationTests {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Test
	public void findPersons() {
		List<PersonPO> persons = personMapper.selectList(null);
		log.info("Persons ==> {}", persons);
	}
	
	@Test
	public void insertPerson() {
		PersonPO person = new PersonPO();
		person.setName("mybatis-plus");
		person.setAge(3);
		person.setAddress("https://mp.baomidou.com");
		person.setUpdateBy("dante");
		int insert = personMapper.insert(person);
		log.info("插入条数 ==> {}", insert);
	}
	
	@Test
	public void findHobbys() {
	}
	

}
