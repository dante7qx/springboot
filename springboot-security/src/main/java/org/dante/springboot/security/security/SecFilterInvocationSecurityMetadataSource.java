package org.dante.springboot.security.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.security.domain.Authority;
import org.dante.springboot.security.domain.Resource;
import org.dante.springboot.security.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 从数据库提取权限和资源，放到resourceMap中
 * 
 * 在Spring Security的整个过滤链启动后，在容器启动的时候，程序就会进入到该类中的init()方法，init调用了loadResourceDefine()方法，
 * 该方法的主要目的是将数据库中的所有资源与权限读取到本地缓存中保存起来！类中的resourceMap就是保存的所有资源和权限的集合，URL为Key，权限作为Value！
 * 
 * @author dante
 *
 */
public class SecFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(SecFilterInvocationSecurityMetadataSource.class);
	
	@Autowired
	private ResourceService resourceService;
	
	private Map<RequestMatcher, Set<ConfigAttribute>> resourceMap = null;
	
	/**
	 * 使用时放开注释
	 */
//	@PostConstruct
	public void init() {
		loadResourceDefine();
	}
	
	/**
	 * 加载所有资源信息
	 */
	private void loadResourceDefine() {
		resourceMap = new HashMap<RequestMatcher, Set<ConfigAttribute>>();
		List<Resource> resources = resourceService.findAll();
		if(resources == null || resources.isEmpty()) {
			return;
		}
		for (Resource resource : resources) {
			String url = resource.getUrl();
			Authority authority = resource.getAuthority();
			if(authority == null) {
				continue;
			}
			RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
			if (resourceMap.containsKey(requestMatcher)) {  
				Set<ConfigAttribute> configAttributes = resourceMap.get(requestMatcher);  
				configAttributes.addAll(authorityToConfigAttribute(authority));
                resourceMap.put(requestMatcher, configAttributes);
			} else {
				resourceMap.put(requestMatcher, authorityToConfigAttribute(authority));
			}
			logger.info("初始化资源权限完成！");
		}
	}
	
	private Set<ConfigAttribute> authorityToConfigAttribute(Authority authority) {
		Set<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>();
		configAttributes.add(new SecurityConfig(SecurityConstant.ROLE_PREFIX + authority.getCode()));
		return configAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object paramObject) throws IllegalArgumentException {
		logger.info("request: " + paramObject);
		HttpServletRequest request = ((FilterInvocation) paramObject).getRequest();
		for (Map.Entry<RequestMatcher,Set<ConfigAttribute>> entry : this.resourceMap.entrySet()) {
			if (((RequestMatcher) entry.getKey()).matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return FilterInvocation.class.isAssignableFrom(paramClass);
	}


}
