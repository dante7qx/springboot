package org.dante.springboot.mongo.service;

import java.util.Map;

import org.dante.springboot.mongo.po.UserPO;
import org.dante.springboot.mongo.template.SpiritMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class UserService extends SpiritMongoTemplate<UserPO>{

	@Override
	protected Criteria buildCriteria(Map<String, Object> filter) {
		Criteria criteria = new Criteria();  
		int age = Integer.parseInt(filter.get("age").toString());
		criteria.and("age").is(age);  
		return criteria;
	}

}
