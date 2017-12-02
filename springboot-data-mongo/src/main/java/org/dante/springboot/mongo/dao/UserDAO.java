package org.dante.springboot.mongo.dao;

import java.util.List;

import org.dante.springboot.mongo.po.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * MongoRepository 的分页，方便简单，方法实现由工具类完成。不适合多个可变查询条件。
 * 
 * @author dante
 *
 */
public interface UserDAO extends MongoRepository<UserPO, Long> {
	
	public UserPO findByUsername(String username);

	@Query("{age : ?0}")
	public List<UserPO> queryByAge(int age);
	
	public Page<UserPO> findByAge(int age, Pageable pageable);

	/**
	 * 只返回 name 字段
	 * 
	 * @param name
	 * @return
	 */
	@Query(value = "{'name' : ?0}", fields = "{'name' : 1}")
	public List<UserPO> queryReturnName(String name);
}
