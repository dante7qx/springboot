package org.dante.springboot.service.impl;

import java.time.Instant;
import java.util.Date;

import org.dante.springboot.dao.HobbyDAO;
import org.dante.springboot.po.HobbyPO;
import org.dante.springboot.po.PersonPO;
import org.dante.springboot.service.RollbackOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RollbackOnlyServiceImpl implements RollbackOnlyService {
	
	@Autowired
	private HobbyDAO hobbyDAO;

	@Override
	@Transactional
	public void saveHobby(Long personId) {
		HobbyPO hobby = new HobbyPO();
		hobby.setHobby("竹、水、茶");
		hobby.setPerson(new PersonPO(personId));
		hobby.setUpdateDate(Date.from(Instant.now()));
		hobbyDAO.save(hobby);
		log.info("Hobby ===> {}", hobby);
		// 构造异常
		throw new RuntimeException("Hobby 持久化错误！");
	}

}
