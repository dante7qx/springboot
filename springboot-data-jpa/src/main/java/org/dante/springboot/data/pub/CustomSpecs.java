package org.dante.springboot.data.pub;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * 自定义查询
 * 
 * @author dante
 *
 */
public class CustomSpecs {

	@SuppressWarnings("unchecked")
	public static <T> Specification<T> searchAuto(final EntityManager entityManager, final T domain) {
		final Class<T> type = (Class<T>) domain.getClass();
		
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 新建Predicate列表存储构造的查询条件
				List<Predicate> predicates = new ArrayList<>();
				// 获取实体类的EntityType
				EntityType<T> entityType = entityManager.getMetamodel().entity(type);
				
				for (Attribute<T, ?> attr : entityType.getDeclaredAttributes()) {
					Object attrValue = this.getValue(domain, attr);
					if(attrValue != null) {
						if(attr.getJavaType() == String.class) {
							if(!StringUtils.isEmpty(attrValue)) {
								predicates.add(cb.like(root.get(this.attribute(entityType, attr.getName(), String.class)), pattern((String) attrValue)));
							}
						} else {
							predicates.add(cb.equal(root.get(this.attribute(entityType, attr.getName(), attrValue.getClass())), attrValue));
						}
					}
				}
				
				return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
			}
			

			/**
			 * 通过反射获取实体类对象对应属性的属性值
			 * 
			 * @param domain
			 * @param attr
			 * @return
			 */
			private <T> Object getValue(T domain, Attribute<T, ?> attr) {
				return ReflectionUtils.getField((Field) attr.getJavaMember(), domain);
			}
			
			/**
			 * 获得实体类的当前属性的SingularAttribute， SingularAttribute包含的是实体类的某个单独属性
			 * 
			 * @param entity
			 * @param fieldName
			 * @param fieldClass
			 * @return
			 */
			private <E,T> SingularAttribute<T, E> attribute(EntityType<T> entity, String fieldName, Class<E> fieldClass) {
				return entity.getDeclaredSingularAttribute(fieldName, fieldClass);
			}
		};
	}
	
	static private String pattern(String str) {
		return "%" + str + "%";
	}

}
