package org.dante.springboot.mongo.dao;

import java.util.List;

import org.dante.springboot.mongo.po.UserPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserDAO extends MongoRepository<UserPO, Long> {

	public UserPO findByUsername(String username);

	@Query("{age : ?0}")
	public List<UserPO> queryByAge(int age);

	/**
	 * 只返回 name 字段
	 * 
	 * @param name
	 * @return
	 */
	@Query(value = "{'name' : ?0}", fields = "{'name' : 1}")
	public List<UserPO> queryReturnName(String name);
}
