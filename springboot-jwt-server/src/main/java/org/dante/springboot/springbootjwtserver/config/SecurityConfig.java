package org.dante.springboot.springbootjwtserver.config;

import org.dante.springboot.springbootjwtserver.filter.JwtAuthenticationTokenFilter;
import org.dante.springboot.springbootjwtserver.security.JwtEntryPoint;
import org.dante.springboot.springbootjwtserver.security.JwtUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	private final JwtEntryPoint jwtEntryPoint;

    public SecurityConfig(JwtEntryPoint jwtEntryPoint) {
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
	public JwtUserDetailService userDetailsService() {
		return new JwtUserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/**
	 * 身份验证实现
	 */
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(daoAuthenticationProvider);
	}
	
	@Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.
			// 由于使用的是JWT，我们这里不需要csrf
			csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorizeRequests ->  authorizeRequests
				.requestMatchers(	// 允许对于网站静态资源的无授权访问
						"/",
						"/*.html",
						"/favicon.ico",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js"
				).permitAll()
				.requestMatchers(HttpMethod.GET).permitAll()
				.requestMatchers("/auth/**").permitAll()	// 对于获取token的rest api要允许匿名访问
				.anyRequest().authenticated())
			// 认证错误，返回401
			.exceptionHandling(
				httpSecurityExceptionHandlingConfigurer -> {
					httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtEntryPoint);
				}
			)
			// 基于token，所以不需要session
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 // 添加JWT filter
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// Cache-Control: no-cache, no-store, max-age=0, must-revalidate，禁用缓存
		http.headers(header -> header.cacheControl(HeadersConfigurer.CacheControlConfig::disable));
		return http.build();
	}
	
	
}
