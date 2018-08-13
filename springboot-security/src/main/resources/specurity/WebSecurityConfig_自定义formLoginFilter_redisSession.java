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
	public SpringSessionBackedSessionRegistry sessionRegistry;
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
				.antMatchers("/resources/static/**","/webjars/**","/loginpage").permitAll()
				.anyRequest().authenticated()
				.and()
			.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin()
				.loginPage("/loginpage")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/mainpage", true)
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
				.addFilterAt(concurrencyFilter(), ConcurrentSessionFilter.class)
				.sessionManagement()
					.sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy())
					.invalidSessionUrl(SecurityConstant.SESSION_TIMEOUT)
		;
	}
	
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
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
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean 
	public SecUsernamePasswordAuthenticationFilter authenticationFilter() {
		SecUsernamePasswordAuthenticationFilter authenticationFilter = new SecUsernamePasswordAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager);
		authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
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
	
	@Bean
	public ConcurrentSessionFilter concurrencyFilter() {
		ConcurrentSessionFilter concurrentSessionFilter = new ConcurrentSessionFilter(sessionRegistry, SecurityConstant.SESSION_TIMEOUT);
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
		ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
		concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);					// 单个用户最大并行会话数
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
		return new RegisterSessionAuthenticationStrategy(sessionRegistry);
	}
}
