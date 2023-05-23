package org.dante.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.util.Lists;
import org.dante.springboot.dto.EmpDTO;
import org.dante.springboot.mapper.EmpMapper;
import org.dante.springboot.mapper.HobbyMapper;
import org.dante.springboot.mapper.PersonMapper;
import org.dante.springboot.po.Address;
import org.dante.springboot.po.Emp;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SpringbootMybatisPlusApplicationTests {
	
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private HobbyMapper hobbyMapper;
	@Autowired
	private EmpMapper empMapper;
	
	@Test
	void findPage() {
		IPage<PersonPO> page = personMapper.selectPageVo(Page.of(1, 2), null);
		log.info("Persons Page ==> {}", page);
	}
	
	@Test
	void findPersons() {
		List<PersonPO> persons = personMapper.selectList(
			new QueryWrapper<PersonPO>()
				.lambda()
				.ge(PersonPO::getAge, 35)
				.ge(PersonPO::getUpdateDate, "2023-04-27 00:00:00")
				.le(PersonPO::getUpdateDate, "2023-04-27 14:20:59"));
		log.info("Persons ==> {}", persons);
	}
	
	@Test
	void selectPersonComplex() {
		List<PersonPO> persons = personMapper.selectPersonComplex();
		log.info("Persons ==> {}", persons);
	}
	
	@Test
	void insertPerson() {
		PersonPO person = new PersonPO();
		person.setName("mybatis-plus");
		person.setAge(62);
		person.setAddress("https://mp.baomidou.com");
		person.setUpdateBy("dante");
		person.setUpdateDate(DateUtil.date());
		int insert = personMapper.insert(person);
		log.info("插入条数 ==> {}, {}", insert, person);
	}
	
	@Test
	void insertBatch() {
		List<PersonPO> persons = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			PersonPO person = new PersonPO();
			person.setName("批量" + i);
			person.setAge(20 + i);
			person.setAddress("天水市-" + i);
			person.setUpdateBy("dante");
			person.setUpdateDate(DateUtil.date());
			persons.add(person);
		}
		boolean result = Db.saveBatch(persons);
		assertTrue(result);
	}
	
	@Test
	void deleteBatch() {
		personMapper.deleteBatchIds(ListUtil.of(4L, 5L));
	}
	
	/**
	 * https://mybatisplusjoin.com/
	 */
	@Test
	void findHobbys() {
		MPJLambdaWrapper<HobbyPO> leftJoin = new MPJLambdaWrapper<HobbyPO>()
			.selectAll(HobbyPO.class)
			.select("p.Name as personName")
			.leftJoin("T_Person p on p.id = t.PersonId");
		List<HobbyPO> hobbys = hobbyMapper.selectJoinList(HobbyPO.class, leftJoin);
		Console.log("Hobby List => {}", hobbys);
	}
	
	@Test
	void findEmp() {
		MPJLambdaWrapper<Emp> wrapper = new MPJLambdaWrapper<Emp>()
				.selectAll(Emp.class)// 查询t_emp表全部字段
				.select(Address::getCity, Address::getAddress)
				.leftJoin(Address.class, Address::getEmpId, Emp::getId)
				.orderByDesc(Emp::getAge);

		List<EmpDTO> userList = empMapper.selectJoinList(EmpDTO.class, wrapper);
		userList.forEach(Console::log);
	}

}
