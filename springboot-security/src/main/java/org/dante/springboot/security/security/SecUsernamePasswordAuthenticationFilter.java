package org.dante.springboot.security.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.security.domain.User;
import org.dante.springboot.security.exception.KaptchaException;
import org.dante.springboot.security.service.LdapAuthenticationService;
import org.dante.springboot.security.service.UserService;
import org.dante.springboot.security.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	@Autowired
	private UserService userService;
	@Autowired
	private LdapAuthenticationService ldapAuthenticationService;
	@Value("${sec.kaptcha}")
	private Boolean kaptchaValid;
	
	public SecUsernamePasswordAuthenticationFilter() {
	}
	
	public Authentication attemptAuthentication(HttpServletRequest request,    
            HttpServletResponse response) throws AuthenticationException {
		if (!"POST".equals(request.getMethod())) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
        if(kaptchaValid) {
        	String kaptcha = request.getParameter("kaptcha");
            String genKaptcha = obtainGenKaptcha(request);
            if(!kaptcha.equalsIgnoreCase(genKaptcha)) {
            	throw new KaptchaException("验证码错误");
            }
        }
        String username = obtainUsername(request);
		String password = obtainPassword(request);
		username = username.trim();
		User user = null;
		try {
			user = userService.findByLoginAccount(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("系统错误，请重试！");
		}
		if(user == null) {
			throw new UsernameNotFoundException(username+"不存在！"); 
		}
		if(user.getLdapUser()) {
			// Ldap认证用户，认证成功后将User密码赋值到用户输入的password
			if(!ldapAuthenticationService.authenticate(username, password)) {
				throw new UsernameNotFoundException(username+"认证失败！");
			} 
			password = user.getPassword();
		} else if(!EncryptUtil.match2(password, user.getPassword())){
			throw new UsernameNotFoundException("密码错误！");
		}
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		super.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }  
	
	private String obtainGenKaptcha(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	}
	
}
