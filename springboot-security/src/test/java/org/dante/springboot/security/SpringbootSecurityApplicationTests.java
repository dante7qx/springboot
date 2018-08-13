package org.dante.springboot.security;

import java.util.List;

import org.dante.springboot.security.dao.AuthorityDao;
import org.dante.springboot.security.dao.ResourceDao;
import org.dante.springboot.security.dao.ResourceSpecs;
import org.dante.springboot.security.dao.RoleDao;
import org.dante.springboot.security.dao.UserDao;
import org.dante.springboot.security.dao.UserSpecs;
import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.domain.AuthorityRole;
import org.dante.springboot.security.domain.Resource;
import org.dante.springboot.security.domain.Role;
import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.util.JpaEntityConvertUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Sets;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootSecurityApplicationTests {
	private Logger logger = LoggerFactory.getLogger(SpringbootSecurityApplicationTests.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	
	@Test
	public void findByAccount() {
		User user = null;
		try {
			user = userDao.findByAccount("ch.sun");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(user.toString());
	}
	
	@Test
	public void findUserByRoleId() {
		List<User> users = userDao.findAll(UserSpecs.queryUserByRoleId(1L));
		Assert.assertNotNull(users);
	}
	
	@Test
	public void testNativeQuery() {
		List<Object[]> results = authorityDao.findAuthorityRoleByRoleId(5L);
		if(!CollectionUtils.isEmpty(results)) {
			try {
				List<AuthorityRole> authRoles = JpaEntityConvertUtils.castEntity(results, AuthorityRole.class);
				Assert.assertNotNull(authRoles);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@Test
	public void findAllAuthority() {
		List<Authority> authoritys = authorityDao.findAll();
		logger.info(authoritys.toString());
	}
	
	@Test
	public void findByParentId() {
		Long pid = 1L;
		try {
			List<Authority> authoritys = authorityDao.findByParentId(pid);
			logger.info("authoritys: {}", authoritys);
			if(!CollectionUtils.isEmpty(authoritys)) {
				for (Authority authority : authoritys) {
					logger.info("Parent Auth Name: {}", authority.getParentAuthority().getName());
				}
			}
		} catch (Exception e) {
			logger.error("findByParentId error.", e);
		}
	}
	
	@Test 
	public void findRootAuthority() {
		try {
			List<Authority> authoritys = authorityDao.findRootAuthority();
			logger.info("authoritys: {}", authoritys);
			if(!CollectionUtils.isEmpty(authoritys)) {
				for (Authority authority : authoritys) {
					logger.info("Parent Auth Name: {}", authority.getParentAuthority().getName());
				}
			}
		} catch (Exception e) {
			logger.error("findByParentId error.", e);
		}
	}
	
	@Test
	public void saveAuthority() {
		Authority auth = authorityDao.findOne(3L);
		auth.setCode("sysmgr.user.update");
		auth.setName("更新用户");
		auth.setShowOrder(2);
		auth.setAuthorityDesc("更新用户");
		auth.setParentAuthority(new Authority(1L));
		authorityDao.save(auth);
	}
	
	@Test
	public void saveRole() {
		Role role = new Role();
		role.setName("测试权限");
		role.setRoleDesc("测试权限描述");
		role.setAuthoritys(Sets.newHashSet(new Authority(2L), new Authority(6L)));
		roleDao.save(role);
	}
	
	@Test
	public void updateRole() {
		Role role = new Role();
		role.setId(5L);
		role.setName("测试权限 - 1");
		role.setRoleDesc("测试权限描述[--*--]");
		role.setAuthoritys(Sets.newHashSet(new Authority(2L), new Authority(3L)));
		roleDao.save(role);
	}
	
	@Test
	public void deleteRoleById() {
		Long id = 6L;
		roleDao.delete(id);
	}
	
	@Test
	public void queryRoleById() {
		Long id = 5L;
		Role role = roleDao.findOne(id);
		Assert.assertNotNull(role);
	}
	
	@Test
	public void quertByResourceId() {
		Resource resource = null;
		Long pid = 1L;
		try {
			resource = resourceDao.findOne(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(resource);
	}
	
	@Test
	public void findResourceTreeByUserId() {
		try {
			Long userId = 1L;
			List<Resource> resources = resourceDao.findAll(ResourceSpecs.findResourceTreeByUserId(userId));
			Assert.assertNotNull(resources);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void findAllParentId() {
		List<Long> pids = resourceDao.findAllParentId();
		logger.info("pids: {}", pids);
		
	}
}
