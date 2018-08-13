package org.dante.springboot.security.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.dante.springboot.security.domain.Resource;
import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class ResourceSpecs {

	/**
	 * 获取登录用户的可见菜单
	 * 
	 * @param userId
	 * @return
	 */
	public static Specification<Resource> findResourceTreeByUserId(Long userId) {
		return new Specification<Resource>() {
			@Override
			public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<Resource, Resource> parentResourceJoin = root
						.join(root.getModel().getSingularAttribute("parentResource", Resource.class), JoinType.LEFT);
				Root<User> userRoot = query.from(User.class);
				SetJoin<User, Role> roleJoin = userRoot.join(userRoot.getModel().getSet("roles", Role.class),
						JoinType.LEFT);
				Predicate authorityIn = root.get("authority").get("id").as(Long.class)
						.in(roleJoin.join("authoritys").get("id").as(Long.class));
				Predicate userIdEq = cb.equal(userRoot.get("id").as(Long.class), userId);
				query.distinct(true);
				query.where(cb.and(authorityIn, userIdEq));
				query.orderBy(cb.asc(parentResourceJoin.get("showOrder")), cb.asc(root.get("showOrder")));
				return query.getRestriction();
			}
		};
	}
}
