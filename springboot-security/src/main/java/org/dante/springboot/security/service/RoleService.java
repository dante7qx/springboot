package org.dante.springboot.security.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dante.springboot.security.dao.AuthorityDao;
import org.dante.springboot.security.dao.RoleDao;
import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.domain.AuthorityRole;
import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.dto.req.RoleReqDto;
import org.dante.springboot.security.dto.resp.RoleAuthorityTreeDto;
import org.dante.springboot.security.dto.resp.RoleRespDto;
import org.dante.springboot.security.dto.resp.RoleTreeDto;
import org.dante.springboot.security.pub.PageReq;
import org.dante.springboot.security.pub.PageResult;
import org.dante.springboot.security.util.JpaEntityConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class RoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;

	/**
	 * 分页查询
	 * 
	 * @param pageReq
	 * @return
	 */
	public PageResult<RoleRespDto> findPage(PageReq pageReq) {
		PageResult<RoleRespDto> result = new PageResult<RoleRespDto>();
		String sortCol = pageReq.getSort();
		String sortDir = pageReq.getOrder();
		Map<String, Object> filter = pageReq.getQ();

		Sort sort = null;
		if (StringUtils.isEmpty(sortCol)) {
			sort = new Sort(Direction.DESC, "id");
		} else if (StringUtils.isEmpty(sortDir) || PageReq.ASC.equalsIgnoreCase(sortDir)) {
			sort = new Sort(Direction.ASC, sortCol);
		} else if (PageReq.DESC.equalsIgnoreCase(sortDir)) {
			sort = new Sort(Direction.DESC, sortCol);
		}
		Pageable pageRequest = new PageRequest(pageReq.getPageNo() - 1, pageReq.getPageSize(), sort);
		Page<Role> pageUser = null;
		if (!CollectionUtils.isEmpty(filter)) {
			pageUser = roleDao.findAll(buildSpecification(filter), pageRequest);
		} else {
			pageUser = roleDao.findAll(pageRequest);
		}
		if (pageUser != null) {
			List<Role> roles = pageUser.getContent();
			if (!CollectionUtils.isEmpty(roles)) {
				for (Role role : roles) {
					RoleRespDto roleRespDto = new RoleRespDto();
					BeanUtils.copyProperties(role, roleRespDto);
					Set<Authority> authoritys = role.getAuthoritys();
					if (!CollectionUtils.isEmpty(authoritys)) {
						for (Authority authority : authoritys) {
							roleRespDto.getAuthorityIds().add(authority.getId());
						}
					}
					result.getRows().add(roleRespDto);
				}
			}
			result.setTotal(pageUser.getTotalElements());
		}
		return result;
	}

	protected Specification<Role> buildSpecification(Map<String, Object> filter) {
		return new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				String name = (String) filter.get("name");

				if (!StringUtils.isEmpty(name)) {
					Predicate nameLike = cb.like(root.get("name").as(String.class), "%" + name.trim() + "%");
					predicates.add(nameLike);
				}
				return predicates.isEmpty() ? cb.conjunction()
						: cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	@Transactional
	public RoleRespDto updateRole(RoleReqDto roleReq) {
		Role role = new Role();
		BeanUtils.copyProperties(roleReq, role);
		Set<Long> authorityIds = roleReq.getAuthorityIds();
		if (!CollectionUtils.isEmpty(authorityIds)) {
			Set<Authority> authoritys = Sets.newHashSet();
			for (Long authorityId : authorityIds) {
				authoritys.add(new Authority(authorityId));
			}
			role.setAuthoritys(authoritys);
		}
		role = roleDao.save(role);
		RoleRespDto roleResp = new RoleRespDto();
		BeanUtils.copyProperties(roleReq, roleResp);
		roleResp.setId(role.getId());
		return roleResp;
	}

	public RoleRespDto queryById(Long id) {
		Role role = roleDao.findOne(id);
		RoleRespDto roleResp = new RoleRespDto();
		BeanUtils.copyProperties(role, roleResp);
		Set<Authority> authoritys = role.getAuthoritys();
		if (!CollectionUtils.isEmpty(authoritys)) {
			for (Authority authority : authoritys) {
				roleResp.getAuthorityIds().add(authority.getId());
			}
		}
		return roleResp;
	}

	@Transactional
	public void deleteByIds(Long[] ids) {
		if (ids == null) {
			return;
		}
		Set<Role> roles = Sets.newHashSet();
		for (Long id : ids) {
			roles.add(new Role(id));
		}
		roleDao.deleteInBatch(roles);
	}

	@Transactional
	public void deleteById(Long id) {
		roleDao.delete(id);
	}

	/**
	 * 获取所有权限及其关联角色的树形模型
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleAuthorityTreeDto> findRoleAuthorityTreeByRoleId(Long roleId) {
		List<RoleAuthorityTreeDto> roleAuthTrees = Lists.newArrayList();
		List<AuthorityRole> authorityRoles = JpaEntityConvertUtils
				.castEntity(authorityDao.findAuthorityRoleByRoleId(roleId), AuthorityRole.class);
		Map<String, RoleAuthorityTreeDto> treeMap = Maps.newLinkedHashMap();
		for (AuthorityRole authorityRole : authorityRoles) {
			RoleAuthorityTreeDto roleAuthTree = new RoleAuthorityTreeDto(authorityRole);
			treeMap.put("_"+authorityRole.getId(), roleAuthTree);
		}
		
		Set<String> keySet = treeMap.keySet();
		Iterator<String> iterNode = keySet.iterator();
		while(iterNode.hasNext()) {
			String key = (String) iterNode.next();
			RoleAuthorityTreeDto tempTree = treeMap.get(key);
			Long pid = tempTree.getPid();
			if(pid == null) {
				roleAuthTrees.add(tempTree);
			} else {
				Set<String> childKeySet = treeMap.keySet();
				Iterator<String> iterChild = childKeySet.iterator();
				while(iterChild.hasNext()) {
					String childKey = (String) iterChild.next();
					RoleAuthorityTreeDto sameTree = treeMap.get(childKey);
					if(childKey.equals("_"+pid)) {
						if(CollectionUtils.isEmpty(sameTree.getChildren())) {
							sameTree.setChildren(Lists.newArrayList(tempTree));
						} else {
							sameTree.getChildren().add(tempTree);
						}
					}
				}
			}
		}
		return roleAuthTrees;
	}
	
	/**
	 * 获取所有角色，树的形式
	 * 
	 * @return
	 */
	public List<RoleTreeDto> findRoleTree() {
		List<RoleTreeDto> trees = Lists.newArrayList();
		List<Role> roles = roleDao.findAll(new Sort(Direction.DESC, "id"));
		if(!CollectionUtils.isEmpty(roles)) {
			for (Role role : roles) {
				RoleTreeDto tree = new RoleTreeDto();
				tree.setId(role.getId());
				tree.setText(role.getName());
				trees.add(tree);
			}
		}
		return trees;
	}
}
