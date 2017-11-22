package org.dante.springboot.data.person.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dante.springboot.data.person.domain.Hobby;
import org.dante.springboot.data.person.domain.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecs {

	/**
	 * 获取来自北京的人
	 * 
	 * @return
	 */
	public static Specification<Person> personFromBeiJing() {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("address"), "北京");
			}
		};
	}

	/**
	 * 根据Hobby查询
	 * 
	 * @param hobby
	 * @return
	 */
	public static Specification<Person> queryPersonByHobby(String queryhobby) {
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ListJoin<Person, Hobby> hobbyJoin = root.join(root.getModel().getList("hobbys", Hobby.class),
						JoinType.LEFT);
				hobbyJoin.on(cb.isFalse(cb.coalesce(hobbyJoin.get("isDelete").as(Boolean.class), false)));
				Predicate eqHobby = cb.equal(hobbyJoin.get("hobby").as(String.class), queryhobby);
				query.where(eqHobby);
				query.orderBy(cb.desc(root.get("age").as(Integer.class)));
				return query.getRestriction();
			}
		};
	}
}
