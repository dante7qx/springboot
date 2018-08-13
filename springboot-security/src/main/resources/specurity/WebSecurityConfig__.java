package org.dante.springboot.security.config;

import org.dante.springboot.security.security.SecPasswordEncoder;
import org.dante.springboot.security.security.SpringSessionBackedSessionRegistry;
import org.dante.springboot.security.security.SecUsernamePasswordAuthenticationFilter;
import org.dante.springboot.security.security.SecurityConstant;
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


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	public SpringSessionBackedSessionRegistry sessionRegistry;
	@Autowired
	private AuthenticationManager authenticationManager;
	
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
			.sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(sessionRegistry)
				.maxSessionsPreventsLogin(false)
				.expiredUrl(SecurityConstant.SESSION_TIMEOUT)
		;
	}
	
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
}
