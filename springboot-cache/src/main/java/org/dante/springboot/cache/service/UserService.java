package org.dante.springboot.cache.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dante.springboot.cache.constant.CacheConsts;
import org.dante.springboot.cache.core.SpiritMultiLevelCacheService;
import org.dante.springboot.cache.dao.UserDAO;
import org.dante.springboot.cache.po.UserPO;
import org.dante.springboot.cache.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames= CacheConsts.USER, keyGenerator = "sptKeyGenerator")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final UserDAO userDAO;
	private final SpiritMultiLevelCacheService multiLevelCacheService;

	@Cacheable(cacheManager = "redissonCacheManager")
	public UserPO findByAccount(String account) {
		return userDAO.findByAccount(account);
	}
	

	@CacheEvict(allEntries = true)
	@Transactional
	public UserVO insert(UserVO userVO) {
		UserPO userPO = new UserPO();
		BeanUtils.copyProperties(userVO, userPO);
		UserPO u = userDAO.save(userPO);
		BeanUtils.copyProperties(u, userVO);
		log.info("添加缓存：{}", userVO);
		return userVO;
	}

	@CacheEvict(allEntries = true)
	@Transactional
	public void delete(Long id) {
		log.info("删除缓存：{}", id);
		userDAO.deleteById(id);
	}
	

	@CacheEvict(allEntries = true)
	@Transactional
	public UserVO update(UserVO userVO) {
		UserPO userPO = new UserPO();
		BeanUtils.copyProperties(userVO, userPO);
		BeanUtils.copyProperties(userDAO.save(userPO), userVO);
		log.info("添加缓存：{}", userVO);
		return userVO;
	}
	
	@Cacheable
	public List<UserPO> findUsers() {
		log.info("没有从缓存中读取所有用户。。。。。。。。。。。。。");
		return userDAO.findAll(Sort.by(Sort.Direction.DESC, "updateDate"));
	}
	
	/**
	 * 空null不进入缓存
	 */
//	@Cacheable(unless = "#result == null")
//	@Cacheable(unless = "#result == null", cacheManager = "redissonCacheManager")
	@Cacheable(unless = "#result == null", cacheManager = "multilevelCacheManager")
	public UserVO findUser(Long id) {
		log.info("没有从缓存中读取指定 [{}] 的用户。。。。。。。。。。。。。", id);
		UserPO userPO = userDAO.findById(id).orElse(null);
		if(userPO == null) return null;
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(userPO, userVO);
		return userVO;
	}

	@CacheEvict(allEntries = true)
	public void evictAllUserCache() {
		log.info("清除 {} 缓存的所有条目", CacheConsts.USER);

	}
}
