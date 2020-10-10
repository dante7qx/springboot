package org.dante.springboot.mongo.dao;

import org.dante.springboot.mongo.po.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * MongoRepository 的分页，方便简单，方法实现由工具类完成。不适合多个可变查询条件。
 * 
 * @author dante
 *
 */
@Repository
public interface UserDAO extends ReactiveCrudRepository<UserPO, Long> {
	
	public Mono<UserPO> findByUsername(String username);

	@Query("{age : ?0}")
	public Flux<UserPO> queryByAge(int age);
	
	public Mono<Page<UserPO>> findByAge(int age, Pageable pageable);

	@Query(value = "{'name' : ?0}", fields = "{'name' : 1}")
	public Flux<UserPO> queryReturnName(Mono<String> name);
}
