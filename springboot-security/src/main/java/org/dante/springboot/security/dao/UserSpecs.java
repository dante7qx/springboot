package org.dante.springboot.security.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {
	public static Specification<User> queryUserByRoleId(Long roleId) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				SetJoin<User, Role> roleJoin = root.join(root.getModel().getSet("roles", Role.class),
						JoinType.LEFT);
				Predicate eqRoleId = cb.equal(roleJoin.get("id").as(Long.class), roleId);
				query.where(eqRoleId);
				query.orderBy(cb.desc(root.get("account").as(String.class)));
				return query.getRestriction();
			}
		};
	}
}
