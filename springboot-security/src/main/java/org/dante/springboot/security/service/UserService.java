package org.dante.springboot.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dante.springboot.security.config.CacheConstant;
import org.dante.springboot.security.dao.UserDao;
import org.dante.springboot.security.dao.UserSpecs;
import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.dto.req.UserReqDto;
import org.dante.springboot.security.dto.resp.UserRespDto;
import org.dante.springboot.security.pub.PageReq;
import org.dante.springboot.security.pub.PageResult;
import org.dante.springboot.security.util.EncryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Cacheable(value = CacheConstant.USER_CACHE_KEY)
	public List<User> findAll() throws Exception {
		return userDao.findAll(new Sort(Sort.Direction.DESC, "name"));
	}
	
	public User findByLoginAccount(String account) throws Exception {
		return userDao.findByAccount(account);
	}
	
	public UserRespDto findByAccount(String account) throws Exception {
		User user = userDao.findByAccount(account);
		if(user == null) {
			return null;
		}
		UserRespDto userResp = new UserRespDto();
		BeanUtils.copyProperties(user, userResp);
		return userResp;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param pageReq
	 * @return
	 */
	public PageResult<UserRespDto> finaPage(PageReq pageReq) {
		PageResult<UserRespDto> result = new PageResult<UserRespDto>();
		String sortCol = pageReq.getSort();
		String sortDir = pageReq.getOrder();
		Map<String, Object> filter = pageReq.getQ();
		
		Sort sort = null;
		if(StringUtils.isEmpty(sortCol)) {
			sort = new Sort(Direction.DESC, "id");
		} else if(StringUtils.isEmpty(sortDir) || PageReq.ASC.equalsIgnoreCase(sortDir)) {
			sort = new Sort(Direction.ASC, sortCol);
		} else if(PageReq.DESC.equalsIgnoreCase(sortDir)) {
			sort = new Sort(Direction.DESC, sortCol);
		}
		Pageable pageRequest = new PageRequest(pageReq.getPageNo() - 1, pageReq.getPageSize(), sort);
		Page<User> pageUser = null;
		if(!CollectionUtils.isEmpty(filter)) {
			pageUser = userDao.findAll(buildSpecification(filter), pageRequest);
		} else {
			pageUser = userDao.findAll(pageRequest);
		}
		if(pageUser != null) {
			List<User> users = pageUser.getContent();
			if(!CollectionUtils.isEmpty(users)) {
				for (User user : users) {
					UserRespDto userRespDto = new UserRespDto();
					BeanUtils.copyProperties(user, userRespDto);
					result.getRows().add(userRespDto);
				}
			}
			result.setTotal(pageUser.getTotalElements());
		}
		return result;
	}
	
	protected Specification<User> buildSpecification(Map<String, Object> filter) {
		return new Specification<User>(){
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = Lists.newArrayList();
				String account = (String) filter.get("account");
				String name = (String) filter.get("name");
				String email = (String) filter.get("email");
				
				if(!StringUtils.isEmpty(account)) {
					Predicate accountLike = cb.like(root.get("account").as(String.class), "%"+account.trim()+"%");
					predicates.add(accountLike);
				}
				if(!StringUtils.isEmpty(name)) {
					Predicate nameLike = cb.like(root.get("name").as(String.class), "%"+name.trim()+"%");
					predicates.add(nameLike);
				}
				if(!StringUtils.isEmpty(email)) {
					Predicate emailLike = cb.like(root.get("email").as(String.class), "%"+email.trim()+"%");
					predicates.add(emailLike);
				}
				
				return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}};
	}
	
	public UserRespDto queryById(Long id) {
		UserRespDto userResp = null;
		User user = userDao.findOne(id);
		if(user != null) {
			userResp = new UserRespDto();
			BeanUtils.copyProperties(user, userResp, "roles");
			Set<Role> roles = user.getRoles();
			if(!CollectionUtils.isEmpty(roles)) {
				for (Role role : roles) {
					userResp.getRoleIds().add(role.getId());
				}
			}
		}
		return userResp;
	}
	
	@Transactional
	public void deleteById(Long id) {
		userDao.delete(id);
	}
	
	@Transactional
	public UserRespDto updateUser(UserReqDto userReq) {
		Long id = userReq.getId();
		User user = null;
		if(id != null) {
			user = userDao.findOne(id);
		} else {
			user = new User();
		}
		BeanUtils.copyProperties(userReq, user, "password");
		if(id == null) {
			user.setPassword(EncryptUtil.bcryptEncrypt(userReq.getPassword()));
		} 
		Set<Long> roleIds = userReq.getRoleIds();
		if(!CollectionUtils.isEmpty(roleIds)) {
			Set<Role> roles = Sets.newHashSet();
			for (Long roleId : roleIds) {
				if(roleId < 0) {
					continue;
				}
				roles.add(new Role(roleId));
			}
			user.setRoles(roles);
		}
		user = userDao.save(user);
		userReq.setId(user.getId());
		UserRespDto userResp = new UserRespDto();
		BeanUtils.copyProperties(userReq, userResp);
		return userResp;
	}
	
	/**
	 * 获取指定角色Id下的用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UserRespDto> findByRoleId(Long roleId) {
		List<UserRespDto> userResps = Lists.newArrayList();
		List<User> users = userDao.findAll(UserSpecs.queryUserByRoleId(roleId));
		if(!CollectionUtils.isEmpty(users)) {
			for (User user : users) {
				UserRespDto userResp = new UserRespDto();
				BeanUtils.copyProperties(user, userResp);
				userResps.add(userResp);
			}
		}
		return userResps;
	}
}
