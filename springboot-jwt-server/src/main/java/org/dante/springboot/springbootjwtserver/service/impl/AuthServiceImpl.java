package org.dante.springboot.springbootjwtserver.service.impl;

import java.util.Date;

import org.dante.springboot.springbootjwtserver.dao.UserDAO;
import org.dante.springboot.springbootjwtserver.po.RolePO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.dante.springboot.springbootjwtserver.security.JwtTokenUtil;
import org.dante.springboot.springbootjwtserver.security.JwtUserDetailService;
import org.dante.springboot.springbootjwtserver.security.JwtUserDetails;
import org.dante.springboot.springbootjwtserver.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	private AuthenticationManager authenticationManager;
    private JwtUserDetailService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserDAO userDAO;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtUserDetailService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDAO = userDAO;
    }

	@Override
	@Transactional
	public UserPO register(UserPO user) {
		final String username = user.getUserName();
        try {
			if(userDAO.findByUserName(username) != null) {
			    return null;
			}
		} catch (Exception e) {
			LOGGER.error("find user {} error.", username, e);
			return null;
		}
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setLastPasswordResetDate(new Date());
        // 普通用户, Role Code: USER
        user.setRoles(Sets.newHashSet(new RolePO(1L)));
        return userDAO.save(user);

	}

	@Override
	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
	}

	@Override
	public String refresh(String oldToken) {
		String refreshedToken = null;
		final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUserDetails user = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
        	refreshedToken = jwtTokenUtil.refreshToken(token);
        }
        return refreshedToken;
	}

}
