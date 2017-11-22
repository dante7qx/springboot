package org.dante.springboot.data.person.dao;

import java.util.List;

import org.dante.springboot.data.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
	
	public List<Person> findByName(String name) throws Exception;
	
	public List<Person> findByAddressLike(String address) throws Exception;
	
	@Query("select p from Person p where p.name = :name and p.address = :address")
	public Person withNameAndAddressQuery(@Param("name") String name, @Param("address")String address) throws Exception;
	
	public Person withNameAndAddressNamedQuery(String name, String address) throws Exception;
	
	@Query(value="select p.id, p.name from T_Person p where p.id = 1", nativeQuery=true)
	public List<Object[]> findTest();
}
