package org.dante.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
     * 配置访问路径
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //设置Spring Security对"/"、"/login"路径不拦截
                .antMatchers("/","/login","/topic").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")//指定登录页面
                .defaultSuccessUrl("/chat")//登录成功后跳转的页面
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
    
    /**
     * 指定用户信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //指定用户名和密码及其角色（密码：123）
        auth.inMemoryAuthentication()
        	.passwordEncoder(passwordEncoder())
                .withUser("dante").password("$2a$10$T9Pb5YaTodn5PEmc/xxvbey3VoNEaWByLNjbGl773DxgRENFpqeri").roles("USER")
                .and()
                .withUser("snake").password("$2a$10$T9Pb5YaTodn5PEmc/xxvbey3VoNEaWByLNjbGl773DxgRENFpqeri").roles("USER");
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}
