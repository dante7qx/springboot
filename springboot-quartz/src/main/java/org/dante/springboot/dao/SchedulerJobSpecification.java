package org.dante.springboot.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dante.springboot.po.SchedulerJobPO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SchedulerJobSpecification {
	
	public static Specification<SchedulerJobPO> querySpecification(Map<String, Object> filter) {
		return new Specification<SchedulerJobPO>() {
			private static final long serialVersionUID = -4352990957019645478L;

			@Override
			public Predicate toPredicate(Root<SchedulerJobPO> root, CriteriaQuery<? extends Object> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				String jobId = (String) filter.get("jobId");
				if (StringUtils.hasLength(jobId)) {
					Predicate jobIdLike = cb.like(root.get("jobId").as(String.class), "%" + jobId.trim() + "%");
					predicates.add(jobIdLike);
				}
				return predicates.isEmpty() ? cb.conjunction()
						: cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
