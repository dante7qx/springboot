package org.dante.springboot.service.impl;

import java.time.Instant;
import java.util.Date;

import org.dante.springboot.dao.HobbyDAO;
import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.dante.springboot.service.IPersonService;
import org.dante.springboot.service.RollbackOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private HobbyDAO hobbyDAO;
	@Autowired
	private RollbackOnlyService rollbackOnlyService;

	/**
	 * 在同一个类中，一个无事务的方法调用另一个有事务的方法，事务是不会起作用的
	 * 
	 * @param person
	 */
	@Override
	@Transactional
	public void savePerson(PersonPO person) {
		buildPerson(person);
		personDAO.save(person);
		log.info("Person ===> {}", person);
		saveHobby(person.getId());
		// 构造异常
		// throw new RuntimeException("Person 持久化错误！");
	}

	@Transactional
	public void saveHobby(Long personId) {
		HobbyPO hobby = new HobbyPO();
		hobby.setHobby("竹、水、茶");
		hobby.setPerson(new PersonPO(personId));
		hobby.setUpdateDate(Date.from(Instant.now()));
		hobbyDAO.save(hobby);
		log.info("Hobby ===> {}", hobby);
		// 构造异常
		// throw new RuntimeException("Hobby 持久化错误！");
	}

	@Transactional
	public void saveHobbyReadOnlyTransaction(Long personId) {
		HobbyPO hobby = new HobbyPO();
		hobby.setHobby("竹、水、茶");
		hobby.setPerson(new PersonPO(personId));
		hobby.setUpdateDate(Date.from(Instant.now()));
		hobbyDAO.save(hobby);
		log.info("Hobby ===> {}", hobby);
		// 构造异常
		throw new RuntimeException("Hobby 持久化错误！");
	}

	/**
	 * 1. 使用 rollbackOnlyService.saveHobby(person.getId()) 会抛出异常
	 * 	  Transaction rolled back because it has been marked as rollback-only
	 * 
	 * 2. 使用 saveHobbyReadOnlyTransaction(person.getId()) 
	 * 	  Person 和 Hobby 都会保存成功，并持久化入库
	 * 
	 * 参考： https://blog.csdn.net/f641385712/article/details/80445912
	 */
	@Override
	@Transactional
	public void savePersonRollbackOnly(PersonPO person) {
		buildPerson(person);
		personDAO.save(person);
		log.info("Person ===> {}", person);
		try {
//			saveHobbyReadOnlyTransaction(person.getId());
			rollbackOnlyService.saveHobby(person.getId());
		} catch (Exception e) {
			log.error("保存 Hobby error!", e.getMessage());
		}
	}
	
	private void buildPerson(PersonPO person) {
		person.setName("事务测试 - " + (int) (1 + Math.random() * (10)));
		person.setAge((int) (1 + Math.random() * (20)));
		person.setAddress("雍和航星园3号楼5层");
	}
	
}
