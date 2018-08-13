package org.dante.springboot.security.config;

import java.util.List;

import org.dante.springboot.security.security.SecPasswordEncoder;
import org.dante.springboot.security.security.SecUsernamePasswordAuthenticationFilter;
import org.dante.springboot.security.security.SecurityConstant;
import org.dante.springboot.security.security.SpringSessionBackedSessionRegistry;
import org.dante.springboot.security.security.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.google.common.collect.Lists;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private SpringSessionBackedSessionRegistry springSessionBackedSessionRegistry;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService();
	}
	
	@Bean 
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		SecPasswordEncoder passwordEncoder = new SecPasswordEncoder(bcryptPasswordEncoder());
		return passwordEncoder;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setHideUserNotFoundExceptions(false);
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 内存中认证
		// auth.inMemoryAuthentication().withUser("dante").password("password").roles("USER");
		auth.authenticationProvider(authenticationProvider());
	}
	
	/**
	 * 1、Clickjacking（点击劫持），设置 X-Frame-Options -> SAMEORIGIN：frame页面的地址只能为同源域名下的页面
	 * 2、XSS（跨站脚本提交）
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers()
				.frameOptions()
				.sameOrigin()
			.and()
			.authorizeRequests()
				.antMatchers("/static/**","/webjars/**","/loginpage").permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin()
				.loginPage("/loginpage")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/mainpage", true)
				.permitAll()
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
//			.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
			.addFilterAt(concurrencyFilter(), ConcurrentSessionFilter.class)
			.sessionManagement()
				.sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
				.invalidSessionUrl(SecurityConstant.SESSION_TIMEOUT)
		;
	}
	
	
	@Bean 
	public SecUsernamePasswordAuthenticationFilter authenticationFilter() {
		SecUsernamePasswordAuthenticationFilter authenticationFilter = new SecUsernamePasswordAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager);
//		authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		authenticationFilter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy());
		authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		return authenticationFilter;
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		authenticationSuccessHandler.setDefaultTargetUrl("/mainpage");
		authenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
		return authenticationSuccessHandler;
	}
	
	@Bean
	public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
		SimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
		authenticationFailureHandler.setDefaultFailureUrl("/loginpage");
		return authenticationFailureHandler;
	}
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// 禁用CSRF保护
			// .csrf().disable()
			.authorizeRequests()
				// 静态资源无授权要求
				.antMatchers("/resources/static/**","/webjars/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
//			.csrf()
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and()
			.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
			.addFilterAt(concurrencyFilter(), ConcurrentSessionFilter.class)
			.sessionManagement()
				.sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
				.invalidSessionUrl(SecurityConstant.SESSION_TIMEOUT)
			;
	}
	*/

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	/*
	@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    */
    
	@Bean
	public ConcurrentSessionFilter concurrencyFilter() {
	//	ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry(), SecurityConstant.SESSION_TIMEOUT);
		ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(springSessionBackedSessionRegistry, SecurityConstant.SESSION_TIMEOUT);
		
		return concurrentSessionFilter;
	}
	
	@Bean
	public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy() {
		List<SessionAuthenticationStrategy> delegateStrategies = Lists.newLinkedList();
		delegateStrategies.add(concurrentSessionControlAuthenticationStrategy());
		delegateStrategies.add(sessionFixationProtectionStrategy());
		delegateStrategies.add(registerSessionAuthenticationStrategy());
		CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy = new CompositeSessionAuthenticationStrategy(delegateStrategies);
		return compositeSessionAuthenticationStrategy;
	}
	
	@Bean
	public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
//		ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
		ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(this.springSessionBackedSessionRegistry);
		concurrentSessionControlAuthenticationStrategy.setMaximumSessions(2);					// 单个用户最大并行会话数
		concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);	// 设置为true时会报错且后登录的会话不能登录，默认为false不报错且将前一会话置为失效
		return concurrentSessionControlAuthenticationStrategy;
	}
	
	@Bean
	public SessionFixationProtectionStrategy sessionFixationProtectionStrategy() {
		SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
		sessionFixationProtectionStrategy.setMigrateSessionAttributes(true);
		return sessionFixationProtectionStrategy;
	}
	
	@Bean
	public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy() {
//		return new RegisterSessionAuthenticationStrategy(sessionRegistry());
		return new RegisterSessionAuthenticationStrategy(this.springSessionBackedSessionRegistry);
	}
	
	/**
	 * 授权配置定义 (配置后有问题，访问未登录页面时无法自动跳到登录页)
	 * .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
	 * 
	 * @return
	 */
/*
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Lists.newArrayList();
		decisionVoters.add(accessDecisionVoter());
		AccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
		return accessDecisionManager;
	}
	
	@Bean
	public AccessDecisionVoter accessDecisionVoter() {
		SecVoter roleVoter = new SecVoter();
		roleVoter.setRolePrefix(SecurityConstant.ROLE_PREFIX);
		return roleVoter;
	}
	
	@Bean
	public FilterInvocationSecurityMetadataSource securityMetadataSource() {
		FilterInvocationSecurityMetadataSource securityMetadataSource = new SecFilterInvocationSecurityMetadataSource();
		return securityMetadataSource;
	}

	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
		filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource());
		return filterSecurityInterceptor;
	}
*/
	
}
