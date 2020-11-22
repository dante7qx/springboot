package org.dante.springboot.cache.service;

import java.util.List;

import org.dante.springboot.cache.constant.RedisCacheConsts;
import org.dante.springboot.cache.dao.UserDAO;
import org.dante.springboot.cache.po.UserPO;
import org.dante.springboot.cache.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames=RedisCacheConsts.FIND_USER_CACHE)
@Transactional(readOnly = true)
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Cacheable(key="caches[0].name.concat('_').concat(#account)")
	public UserPO findByAccount(String account) {
		return userDAO.findByAccount(account);
	}
	
	@Caching(put = @CachePut(key="caches[0].name.concat('_').concat(#userPO.getId())"), 
			evict = @CacheEvict(key="caches[0].name"))
	@Transactional
	public UserPO insert(UserPO userPO) {
		UserPO u = userDAO.save(userPO);
		logger.info("添加缓存：{}", u);
		return u;
	}
	
	@Caching(evict={
			@CacheEvict(key="caches[0].name.concat('_').concat(#id)"),
			@CacheEvict(key="caches[0].name")
	})
	@Transactional
	public void delete(Long id) {
		logger.info("删除缓存：{}", id);
		userDAO.deleteById(id);
	}
	
	
	@Caching(put = @CachePut(key="caches[0].name.concat('_').concat(#userVO.getId())"), 
			evict = @CacheEvict(key="caches[0].name"))
	@Transactional
	public UserVO update(UserVO userVO) {
		UserPO userPO = new UserPO();
		BeanUtils.copyProperties(userVO, userPO);
		BeanUtils.copyProperties(userDAO.save(userPO), userVO);
		logger.info("添加缓存：{}", userVO);
		return userVO;
	}
	
//	@Cacheable(key="'"+RedisCacheConsts.FIND_USER_CACHE+"'")
	@Cacheable(key="caches[0].name")
	public List<UserPO> findUsers() {
		logger.info("没有从缓存中读取所有用户。。。。。。。。。。。。。");
		return userDAO.findAll(Sort.by(Sort.Direction.DESC, "updateDate"));
	}
	
	/**
	 * 空null不进入缓存
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(key="caches[0].name.concat('_').concat(#id)", unless = "#result == null")
	public UserVO findUser(Long id) {
		logger.info("没有从缓存中读取指定 [{}] 的用户。。。。。。。。。。。。。", id);
		UserPO userPO = userDAO.findById(id).orElse(null);
		if(userPO == null) return null;
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(userPO, userVO);
		return userVO;
	}
	
}
