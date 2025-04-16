package org.dante.springboot.service;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.dante.springboot.dao.DpCityCodeDAO;
import org.dante.springboot.po.DpCityCodePO;
import org.dante.springboot.vo.PageReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service
@Transactional(readOnly = true)
public class DpCityCodeService {

	private final DpCityCodeDAO dpCityCodeDAO;

	public DpCityCodeService(DpCityCodeDAO dpCityCodeDAO) {
		this.dpCityCodeDAO = dpCityCodeDAO;
	}

	public Page<DpCityCodePO> selectCityCodePage(PageReq param) {
		Sort sort = Sort.by(Sort.Direction.ASC, "pk.code");
		Pageable pageable = PageRequest.of(param.getPageNo() - 1, param.getPageSize(), sort);

		Page<DpCityCodePO> page = dpCityCodeDAO.findAll((root, query, cb) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if (StrUtil.isNotEmpty(param.getKeywords())) {
				predicates.add(cb.or(cb.like(root.get("pk").get("code"), "%" + param.getKeywords() + "%"),
						cb.like(root.get("pk").get("ch"), "%" + param.getKeywords() + "%"),
						cb.like(root.get("pk").get("en"), "%" + param.getKeywords() + "%")));
			}
			return cb.and(predicates.toArray(new Predicate[0]));

		}, pageable);

		return page;
	}

	public List<DpCityCodePO> selectCityCodes() {
		return dpCityCodeDAO.findAll(Sort.by("pk.code"));
	}
	
	public DpCityCodePO selectByCode(String code) {
		return dpCityCodeDAO.findByPkCode(code);
	}

	@Transactional
	public void batchInsert(List<DpCityCodePO> pos) {
		if (dpCityCodeDAO.count() == 0) {
			dpCityCodeDAO.saveAll(pos);
		}
	}

}
