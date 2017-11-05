package org.dante.springboot.mongo.dao;

import org.dante.springboot.mongo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDAO extends MongoRepository<UserPO, Long> {

	public UserPO findByUsername(String username);

}
