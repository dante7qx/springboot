package org.dante.springboot.mongo.template;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dante.springboot.mongo.page.PageReq;
import org.dante.springboot.mongo.page.SpiritMongoPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

public abstract class SpiritMongoTemplate<T> {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public Page<T> queryPage(PageReq pageReq, Class<T> clazz) {
		int pageNo = pageReq.getPageNo();
		int pageSize = pageReq.getPageSize();
		String sortCol = pageReq.getSort();
		String sortDir = pageReq.getOrder();
		Map<String, Object> q = pageReq.getQ();
		
		Query query = new Query();
		if(q != null && !q.isEmpty()) {
			Criteria criteria = buildCriteria(q);
			query.addCriteria(criteria);
		}
		Sort sort = buildSorter(sortCol, sortDir);
		if (sort != null) {
			query.with(sort);
		}
		SpiritMongoPageable pageable = new SpiritMongoPageable();
		pageable.setPagenumber(pageNo);
		pageable.setPagesize(pageSize);
		if (sort != null) {
			pageable.setSort(sort);
		}
		Long count = mongoTemplate.count(query, clazz);
		List<T> list = mongoTemplate.find(query.with(pageable), clazz);
		Page<T> resp = new PageImpl<T>(list, pageable, count);
		return resp;
	}
	
	/**
	 * 构造查询条件
	 * 
	 * @param filter
	 * @return
	 */
	protected abstract Criteria buildCriteria(Map<String, Object> filter);
	
	/**
	 * 构造排序, 默认按照主键（id）倒序（desc）
	 * 
	 * @param sortCol
	 * @param sortDir
	 * @return
	 */
	private Sort buildSorter(String sortCol, String sortDir) {
		if (StringUtils.isEmpty(sortCol)) {
			return Sort.by(Direction.DESC, "id");
		}
		Sort sort = null;
		String[] sortColArr = sortCol.trim().split(",");
		String[] sortDirArr = sortDir.trim().split(",");
		int sortColLength = sortColArr.length;
		for (int i = 0; i < sortColLength; i++) {
			String col = sortColArr[i].trim();
			String dir = sortDirArr[i];
			if (i == 0) {
				sort = Sort.by(buildDirection(dir), col);
				continue;
			}
			if (sort != null) {
				sort.and(Sort.by(buildDirection(dir), col));
			}
		}
		return sort;
	}
	
	/**
	 * 构造排序
	 * 
	 * @param sortDir
	 * @return
	 */
	private Direction buildDirection(String sortDir) {
		Direction direction = Direction.ASC;
		switch (sortDir.trim().toLowerCase(Locale.ENGLISH)) {
		case "asc":
			direction = Direction.ASC;
			break;
		case "desc":
			direction = Direction.DESC;
			break;
		default:
			break;
		}
		return direction;
	}
}
