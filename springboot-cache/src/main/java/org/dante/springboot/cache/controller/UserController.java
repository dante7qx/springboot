package org.dante.springboot.cache.controller;

import cn.hutool.core.lang.Console;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.dante.springboot.cache.constant.CacheConsts;
import org.dante.springboot.cache.po.UserPO;
import org.dante.springboot.cache.service.UserService;
import org.dante.springboot.cache.vo.UserVO;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final CacheManager cacheManager;
	
	@GetMapping("/user/all")
	public List<UserPO> findUsers() {
		return userService.findUsers();
	}
	
	@GetMapping("/user/{id}")
	public UserVO findById(@PathVariable Long id) {
		return userService.findUser(id);
	}
	
	@GetMapping("/user_account/{account}")
	public UserPO findByAccount(@PathVariable String account) {
		return userService.findByAccount(account);
	}
	
	@GetMapping("/user/add")
	public UserVO addUser() {
		UserVO vo = new UserVO("帐号"+Math.random(), "名称"+Math.random(), 32,  BigDecimal.valueOf(87.62));
		return userService.insert(vo);
	}
	
	@GetMapping("/user/update/{id}")
	public UserVO updateUser(@PathVariable Long id) {
		UserVO userVO = userService.findUser(id);
		userVO.setAccount("更新帐号"+Math.random());
		userVO.setName("更新名称"+Math.random());
		return userService.update(userVO);
	}
	
	@GetMapping("/user/del/{id}")
	public void deleteById(@PathVariable Long id) {
		userService.delete(id);
	}

	@GetMapping("/stats")
	public String cacheStats() {
		CaffeineCache cache = (CaffeineCache) cacheManager.getCache(CacheConsts.USER);
        assert cache != null;
        Cache<?, ?> nativeCache = cache.getNativeCache();
        for (Map.Entry<?, ?> entry : nativeCache.asMap().entrySet()) {
            Object k = entry.getKey();
            Object v = entry.getValue();
            Console.log(k + " —> " + v);
        }
        return nativeCache.stats().toString();
	}

	@GetMapping("/clear")
	public void clearAll() {
		userService.evictAllUserCache();
	}
}	
