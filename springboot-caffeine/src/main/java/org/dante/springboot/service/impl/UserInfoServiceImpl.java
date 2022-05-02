package org.dante.springboot.service.impl;

import java.util.HashMap;
import java.util.Objects;

import org.dante.springboot.service.UserInfoService;
import org.dante.springboot.vo.UserInfoVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserInfoServiceImpl implements UserInfoService {
	
//	@Autowired
//  private Cache<String, Object> caffeineCache;

	/**
     * 模拟数据库存储数据
     */
    private HashMap<Integer, UserInfoVO> userInfoVOMap = new HashMap<>();

    @Override
    @CachePut(key = "#userInfoVO.id")
    public UserInfoVO addUserInfo(UserInfoVO userInfoVO) {
        log.info("create");
        userInfoVOMap.put(userInfoVO.getId(), userInfoVO);
        return userInfoVO;
    }

    @Override
    @Cacheable(key = "#id")
    public UserInfoVO getByName(Integer id) {
        log.info("get");
        return userInfoVOMap.get(id);
    }

    @Override
    @CachePut(key = "#userInfoVO.id")
    public UserInfoVO updateUserInfo(UserInfoVO userInfoVO) {
        log.info("update");
        if (!userInfoVOMap.containsKey(userInfoVO.getId())) {
            return null;
        }
        // 取旧的值
        UserInfoVO oldUserInfoVO = userInfoVOMap.get(userInfoVO.getId());
        // 替换内容
        if (!Objects.isNull(oldUserInfoVO.getAge())) {
            oldUserInfoVO.setAge(userInfoVO.getAge());
        }
        if (StringUtils.hasText(oldUserInfoVO.getName())) {
            oldUserInfoVO.setName(userInfoVO.getName());
        }
        if (StringUtils.hasText(oldUserInfoVO.getSex())) {
            oldUserInfoVO.setSex(userInfoVO.getSex());
        }
        // 将新的对象存储，更新旧对象信息
        userInfoVOMap.put(oldUserInfoVO.getId(), oldUserInfoVO);
        // 返回新对象信息
        return oldUserInfoVO;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {
        log.info("delete");
        userInfoVOMap.remove(id);
    }

}
